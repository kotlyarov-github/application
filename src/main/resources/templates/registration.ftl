<#import "part/common.ftl" as c>
<#import "part/login.ftl" as l>

<@c.page>
Add new user
${message!""}
<@l.login "/registration" />
</@c.page>