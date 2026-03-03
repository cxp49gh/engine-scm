package com.engine.scm.template;

public interface RuntimeParamDefService {

    /**
     * 根据引用 ID 获取参数定义
     */
    RuntimeParamDefinition get(String ref);

    /**
     * 是否存在（发布 / Dry-Run 前校验用）
     */
    boolean exists(String ref);

    /**
     * 创建或更新参数定义（后台管理用）
     */
    RuntimeParamDefinition save(RuntimeParamDefinition def);
}
