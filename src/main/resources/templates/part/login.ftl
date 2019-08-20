<#macro login path isRegisterForm>
    <form method="post" action="${path}">

        <div class="from-group row">
            <label for="inlineFormInput" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" name="username" placeholder="Username"/>
            </div>
        </div>

        <div class="from-group row">
            <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-5">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
        <#if !isRegisterForm>
            <a href="/registration">Registration</a>
        </#if>

        <button class="btn btn-primary" type="submit">
            <#if !isRegisterForm>
                Sign In
            <#else>
                Create
            </#if>
        </button>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>


<#macro logout>
    <form method="post" action="/logout">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>