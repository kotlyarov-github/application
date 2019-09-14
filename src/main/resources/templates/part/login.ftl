<#macro login path isRegisterForm>
    <form method="post" action="${path}">

        <div class="from-group row">
            <label for="inlineFormInput" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-5">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       value="<#if user??>${user.username}</#if>" name="username" placeholder="Username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="from-group row">
            <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-5">
                <input type="password" class="form-control  ${(passwordError??)?string('is-invalid', '')}"
                       name="password" placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="from-group row">
                <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-5">
                    <input type="password" class="form-control  ${(password2Error??)?string('is-invalid', '')}"
                           name="password2" placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="from-group row">
                <label class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-5">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                           value="<#if email??>${user.email}</#if>" name="email" placeholder="some@some.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-5">
                <div class="g-recaptcha" data-sitekey="6LdTlbYUAAAAAJdmIp-tzVg35kian1PUGfZJ4N_P"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
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