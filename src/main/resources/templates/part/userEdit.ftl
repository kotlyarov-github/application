<#macro edit path isEditForm>
    <form action="${path}" method="post">
        <input type="hidden" value="${user.id}" name="userId"/>
        <input type="hidden" value="${_csrf.token}" name="_csrf"/>
        <#if isEditForm>
            User editor
            <input type="text" value="${user.username}" name="username"/>
            <#list roles as role>
                <div>
                    <label><input type="checkbox"
                                  name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
                </div>
            </#list>
            <button type="submit">Save</button>
        <#else>
            Are you sure you want to delete ${user.username}?
            <button type="submit">Delete</button>
        </#if>
    </form>
</#macro>