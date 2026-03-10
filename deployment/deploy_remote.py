import argparse
import os
import pathlib
import shutil
import subprocess
import tarfile
import tempfile
from typing import Iterable

PROJECT_ROOT = pathlib.Path(__file__).resolve().parents[3]


def run(cmd: list[str]) -> None:
    subprocess.run(cmd, check=True)


def ensure_exec(name: str) -> None:
    if shutil.which(name) is None:
        raise SystemExit(f"Missing executable: {name}")


def should_exclude(path: pathlib.Path, excludes: Iterable[str]) -> bool:
    normalized = str(path).replace("\\", "/")
    for pattern in excludes:
        if pathlib.PurePosixPath(normalized).match(pattern):
            return True
    return False


def build_archive(archive_path: pathlib.Path, root: pathlib.Path, excludes: list[str]) -> None:
    with tarfile.open(archive_path, "w:gz") as tar:
        for p in root.rglob("*"):
            if p.is_dir():
                continue
            rel = p.relative_to(root)
            if should_exclude(rel, excludes):
                continue
            tar.add(p, arcname=str(rel).replace("\\", "/"))


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--host", required=True)
    parser.add_argument("--user", default="root")
    parser.add_argument("--port", default="22")
    parser.add_argument("--remote-dir", default="/www/wwwroot/ai-interview")
    parser.add_argument("--compose-dir", default="backend/ai-interview-controller/deployment")
    parser.add_argument("--project-root", default=str(PROJECT_ROOT))
    parser.add_argument("--no-build", action="store_true")
    args = parser.parse_args()

    ensure_exec("ssh")
    ensure_exec("scp")

    root = pathlib.Path(args.project_root).resolve()
    if not root.exists():
        raise SystemExit(f"Invalid project root: {root}")

    excludes = [
        ".git/**",
        "**/node_modules/**",
        "**/dist/**",
        "**/target/**",
        "**/*.log",
        "**/.DS_Store",
        "**/.idea/**",
        "**/.vscode/**",
        "**/*.iml",
        "**/logs/**",
    ]

    ssh_target = f"{args.user}@{args.host}"
    ssh_base = ["ssh", "-p", str(args.port), ssh_target]
    scp_base = ["scp", "-P", str(args.port)]

    with tempfile.TemporaryDirectory() as tmp:
        archive_path = pathlib.Path(tmp) / "ai-interview.tar.gz"
        build_archive(archive_path, root, excludes)

        remote_tmp = f"/tmp/ai-interview-{os.getpid()}.tar.gz"
        run([*scp_base, str(archive_path), f"{ssh_target}:{remote_tmp}"])

        build_flag = "0" if args.no_build else "1"
        remote_cmd = (
            f"set -e; "
            f"mkdir -p '{args.remote_dir}'; "
            f"cd '{args.remote_dir}'; "
            f"tar -xzf '{remote_tmp}'; "
            f"rm -f '{remote_tmp}'; "
            f"cd '{args.remote_dir}/{args.compose_dir}'; "
            f"chmod +x deploy.sh || true; "
            f"DEPLOY_BUILD='{build_flag}' ./deploy.sh; "
        )
        run([*ssh_base, remote_cmd])


if __name__ == "__main__":
    main()
