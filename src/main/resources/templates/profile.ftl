<#import "part/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    <#if message??>
        <p>${message!}</p>
    </#if>

    <form method="post">
        <div class="from-group row">
            <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-5">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
        <div class="from-group row">
            <label class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-5">
                <input type="email" class="form-control" name="email" placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Save</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>

</@c.page>