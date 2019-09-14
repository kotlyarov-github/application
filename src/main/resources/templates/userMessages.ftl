<#import "part/common.ftl" as c>
<#include "part/security.ftl">

<@c.page>
    <#if isCurrentUser>
        <#include "part/messageEdit.ftl" />
    </#if>
    <#include "part/messageList.ftl" />
</@c.page>