#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
COMPOSE_FILE="$ROOT_DIR/deployment/docker-compose.yml"
docker compose -f "$COMPOSE_FILE" down
if [ "${DEPLOY_BUILD:-1}" = "1" ]; then
  docker compose -f "$COMPOSE_FILE" up -d --build
else
  docker compose -f "$COMPOSE_FILE" up -d
fi
