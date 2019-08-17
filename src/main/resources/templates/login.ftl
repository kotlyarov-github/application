<#import "part/common.ftl" as c>
<#import "part/login.ftl" as l>

<@c.page>
    <@l.login "/login"/>
Login
<a href="/registration">Registration</a>
</@c.page>