
    <div class="modal fade" id="waarschuwingsModal" tabindex="-1" role="dialog" aria-labelledby="waarschuwingsModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="waarschuwingsModalLabel"></h2>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                    <div class="modal-body" id="waarschuwingsModalBody">
                    </div>
                <div class="modal-footer">
                    <a class="btn btn-primary btn" id="doorgaan" href="${contextPath} />"></a>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="logoutModalLabel">Uitloggen</h2>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                    <div class="modal-body">
                    Weet je zeker dat je wilt uitloggen?
                    </div>
                <div class="modal-footer">
                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <form class="form-inline" id="logoutform" action="${contextPath}/logout" method="post">
                                <input name="utf8" type="hidden" value="✓">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </c:if>
                        <button class="btn btn-outline-primary">
                        Uitloggen
                       </button>
                   </form>
                </div>
            </div>
        </div>
    </div>
    <script src="${contextPath}\resources\js\jquery.js" type='text/javascript'></script>