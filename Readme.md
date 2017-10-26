## 修改历史

### 2017-10-26

1. 修改apollo的加载时许为在 初始化env之后执行，这样就可以使用conditional的标签
2. 如果系统变量中含有 app.id 则以系统优先


## 使用 springBoot 封装 apollo-client

### 主要修改

1. 修改 app.id 获取路径为 env中，并默认为 spring.application.name
2. 增加 apollo.space.commons 配置公共命名空间，且优先级为最低
3. 增加 apollo.space.private.need.default 配置是否添加默认已有空间到私有空间中
4. 增加 apollo.space.privates 配置私有命名空间



