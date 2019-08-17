<#import "part/common.ftl" as c>
<#import "part/login.ftl" as l>

<@c.page>
    Add new user
    <@l.login "/registration"/>
    <#if message??>
        <p style="color: red">${message!}</p>
    </#if>
</@c.page>