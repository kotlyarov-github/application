<#import "part/common.ftl" as c>
<#import "part/userEdit.ftl" as e>

<@c.page>
    List of users
    <table>
        <thread>
            <tr>
                <th>User</th>
                <th>Role</th>
                <th></th>
            </tr>
        </thread>
        <body>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td> <#list user.roles as role> ${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
                <td>
                    <input type="hidden" value="${user.id}" name="userId">
                    <a href="/user/delete/${user.id}" class="btn btn-danger btn-sm enable" tabindex="-1" role="button"
                       aria-disabled="true">Delete user</a>
                </td>
            </tr>
        </#list>
        </body>
    </table>
</@c.page>