<#include "security.ftl">

<div class="card-columns" id="message-list" xmlns="http://www.w3.org/1999/html">
    <#list messages as message>
        <div class="card my-3" data-id="${message.id}" style="width: 15rem;">
            <#if message.filename?exists>
                <img class="card-img-top" src="/img/${message.filename}"/>
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br/>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">Edit</a>
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>
