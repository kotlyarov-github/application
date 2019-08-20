<#import "part/common.ftl" as c>
<#import "part/login.ftl" as l>

<@c.page>
    <div class="mb-1">Add new user</div>
    <@l.login "/registration" true />
    <#if message??>
        <p style="color: red">${message!}</p>
    </#if>
</@c.page>