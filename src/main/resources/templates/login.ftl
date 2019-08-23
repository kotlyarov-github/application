<#import "part/common.ftl" as c>
<#import "part/login.ftl" as l>

<@c.page>
    <#if message??>
        <p style="color: red">${message!}</p>
    </#if>
    <@l.login "/login" false/>
</@c.page>