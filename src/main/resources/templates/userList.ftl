<#import "part/common.ftl" as c>

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
            </tr>
        </#list>
        </body>
    </table>
</@c.page>