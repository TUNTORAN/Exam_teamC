<!-- 追加部分「クラス管理」 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                クラス登録
            </h2>

            <form action="ClassNumCreateExecute.action" method="post">

                <div class="row px-4 mb-3">
                    <label class="col-2 col-form-label">クラス番号</label>
                    <div class="col-4">
                        <input type="text" name="class_num" class="form-control" required>
                    </div>
                </div>

                <div class="px-4">
                    <button class="btn btn-primary">登録</button>
                </div>

            </form>

        </section>
    </c:param>
</c:import>