package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.MpAnswer;
import com.ruoyi.ai.domain.MpRequest;

public interface IAiService {
    MpAnswer chat(MpRequest mpRequest,String openid);
}
