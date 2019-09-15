<#import "part/common.ftl" as c>
<#include "part/security.ftl">

<@c.page>
    <#if !isActivated >
        user not activated
    <#else>
        <div class="form-row">
            <div class="form-group col-md-6">
                <form method="get" action="/main" class="form-inline">
                    <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                           placeholder="Serch by tag"/>
                    <button type="submit" class="btn btn-primary ml-2">Search</button>
                </form>
            </div>
        </div>

        <#include "part/messageEdit.ftl" />
        <#include "part/messageList.ftl" />
    </#if>
</@c.page>