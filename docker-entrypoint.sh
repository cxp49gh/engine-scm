#!/bin/bash
set -e

# 你可以做一些初始化操作
# 比如生成配置文件、检查数据库状态等

# 最终执行目标程序，并传递参数
# JAVA_TOOL_OPTIONS 是从环境变量获取
exec java $JAVA_TOOL_OPTIONS -jar /data/lib/road-data-branch-management.jar "$@"