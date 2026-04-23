<!-- 追加部分「クラス管理」 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                クラス管理
            </h2>

            <div class="my-2 text-end px-4">
                <a href="ClassNumCreate.action">新規登録</a>
            </div>

            <!-- 一覧 -->
            <c:choose>

                <c:when test="${class_num_set.size() > 0}">
                    <div>クラス数：${class_num_set.size()}件</div>

                    <table class="table table-hover">
                        <tr>
                            <th>クラス番号</th>
                            <th></th>
                        </tr>

                        <c:forEach var="num" items="${class_num_set}">
                            <tr>
                                <td>${num}</td>

                                <td>
                                    <a href="ClassNumDelete.action?class_num=${num}">
                                        削除
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>

                <c:otherwise>
                    <div>クラス情報が存在しませんでした。</div>
                </c:otherwise>

            </c:choose>

        </section>
    </c:param>
</c:import>