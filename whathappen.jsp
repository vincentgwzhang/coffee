<!-- account/referafriend/account/home.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="zpbp" tagdir="/WEB-INF/tags/zooplus/basicPages" %>
<%@ taglib prefix="mzp" tagdir="/WEB-INF/tags/zooplus/myzooplus" %>
<%@ taglib prefix="loy" tagdir="/WEB-INF/tags/zooplus/loyalty" %>

<c:set var="rewardPoints" value="${referrerReward.value}"/>
<c:set var="rewardDiscount" value="${referentReward.value}"/>
<c:set var="iconUrl" value="${mediaServerDomainUrl}/image/loyalty"/>

<spring:message var="pagetitle" code="shop.loyalty.refer-a-friend.mainTitle"/>
<spring:message var="pagesubtitle" code="shop.loyalty.refer-a-friend.bannersubtitle" arguments="${rewardPoints}"/>

<zpbp:loyResponsiveBasicPage pagetitle="${pagetitle}"
                             pagesubtitle="${pagesubtitle}"
                             pagebannerimage="/image/loyalty/banners/myzpl-refer-a-friend-large.png"
                             pagebannerimageretina="/image/loyalty/banners/myzpl-refer-a-friend-large@2x.png"
                             pagebannerimageposition="bottom"
                             menuCssClass="loy-nav-account"
                             pageclass="page__mzp_refer_a_friend">

    <jsp:attribute name="lhsNavigation">
       <c:import url="/part/mzp_navigation.htm">
            <c:param name="selectedSection" value="loy.friendship.navigation.refer-a-friend.home" />
        </c:import>
    </jsp:attribute>

    <jsp:attribute name="modalsWrapper">
        <script language="JavaScript">
            $(document).ready(function () {
                $.ajaxSetup({cache: true});
                $.getScript('//connect.facebook.net/${sessionScope.locale}/sdk.js', function () {
                    FB.init({
                        appId: '${facebookAppId}',
                        autoLogAppEvents: true,
                        xfbml: true,
                        version: 'v2.10'
                    });
                });
            });

            $("#fbsharelink0").click(function () {
                FB.ui({
                    method: 'share',
                    display: 'popup',
                    href: '${referralLink.link}',
                }, function (response) {
                });
            });

            $("#fbsharelink1").click(function () {
                FB.ui({
                    method: 'share',
                    display: 'popup',
                    href: '${referralLink.link}',
                }, function (response) {
                });
            });

            $("#fbsharelink2").click(function () {
                FB.ui({
                    method: 'share',
                    display: 'popup',
                    href: '${referralLink.link}',
                }, function (response) {
                });
            });

            $("#fbsharelink3").click(function () {
                FB.ui({
                    method: 'share',
                    display: 'popup',
                    href: '${referralLink.link}',
                }, function (response) {
                });
            });

            if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                $("#msgsharelink").click(function () {
                    window.open('fb-messenger://share?link=' + encodeURIComponent('${referralLink.link}') + '&app_id=' + encodeURIComponent('${facebookAppId}'));
                })
            } else {
                $("#msgsharelink").click(function () {
                    FB.ui({
                        method: 'send',
                        display: 'popup',
                        link: '${referralLink.link}',
                    }, function (response) {
                    });
                })
            }

        </script>
    </jsp:attribute>

    <jsp:body>
        <div class="rewards-text">
            <p><spring:message code="shop.loyalty.refer-a-friend.text.p1" arguments="${rewardDiscount}"/></p>
            <p><spring:message code="shop.loyalty.refer-a-friend.text.p2" arguments="${rewardPoints}"/></p>
        </div>
        <div class="social-buttons-wrapper">
            <div class="padded-container">
                <div class="link-top-label">
                    <spring:message code="shop.loyalty.refer-a-friend.shareyourlink"/>
                </div>
                <div class="link-container">
                    <div><textarea class="link-box" readonly>${referralLink.link}</textarea></div>
                    <div class="copy-button"><spring:message code="shop.loyalty.refer-a-friend.copy"/></div>
                </div>
                <div class="separator-or"><spring:message code="shop.loyalty.refer-a-friend.or"/></div>
                <div class="social-buttons">
                    <c:if test="${facebookSharingEnabled}">
                        <a class="btn bold white facebook sharebutton" id="fbsharelink0" data-track="facebook_share">
                            <spring:message code="shop.loyalty.refer-a-friend.facebook"/>
                            <img src="${iconUrl}/raf/icon--fb.png"
                                 srcset="${iconUrl}/raf/icon--fb.png 1x,
                                ${iconUrl}/raf/icon--fb@2x.png 2x" class="fb-icon">
                        </a>

                        <a class="btn bold white facebook sharebutton" id="fbsharelink1" data-track="facebook_share">
                            <spring:message code="shop.loyalty.refer-a-friend.facebook"/>
                            <img src="${iconUrl}/raf/icon--fb.png"
                                 srcset="${iconUrl}/raf/icon--fb.png 1x,
                                ${iconUrl}/raf/icon--fb@2x.png 2x" class="fb-icon" />
                        </a>

                        <a class="btn bold white facebook sharebutton" id="fbsharelink2" href="#" >
                            <spring:message code="shop.loyalty.refer-a-friend.facebook"/>
                            <img src="${iconUrl}/raf/icon--fb.png"
                                 srcset="${iconUrl}/raf/icon--fb.png 1x,
                                ${iconUrl}/raf/icon--fb@2x.png 2x" class="fb-icon" />
                        </a>

                            <img id="fbsharelink3" src="${iconUrl}/raf/icon--fb.png"
                                 srcset="${iconUrl}/raf/icon--fb.png 1x,
                                ${iconUrl}/raf/icon--fb@2x.png 2x" class="fb-icon" />
                    </c:if>
                    <c:if test="${messengerSharingEnabled}">
                        <a class="btn bold white messenger sharebutton" id="msgsharelink" href="#" data-track="messenger_share">
                            <spring:message code="shop.loyalty.refer-a-friend.messenger"/>
                            <img src="${iconUrl}/raf/icon--messenger.png"
                                 srcset="${iconUrl}/raf/icon--messenger.png 1x,
                                ${iconUrl}/raf/icon--messenger@2x.png 2x" class="msg-icon">
                        </a>
                    </c:if>
                    <c:if test="${twitterSharingEnabled and twitterShare != null}">
                        <a class="btn bold white tweet sharebutton" id="tweetsharelink" target="_blank" href="${twitterShare.link}" data-track="twitter_share">
                            <spring:message code="shop.loyalty.refer-a-friend.twitter"/>
                            <img src="${iconUrl}/raf/icon--twitter.png"
                                 srcset="${iconUrl}/raf/icon--twitter.png 1x,
                                ${iconUrl}/raf/icon--twitter@2x.png 2x" class="tw-icon">
                        </a>
                    </c:if>
                    <c:if test="${emailSharingEnabled and emailShare != null}">
                        <a class="btn bold white e-mail sharebutton" id="mailsharelink" target="_blank" href="${emailShare.link}" data-track="email_share">
                            <spring:message code="shop.loyalty.refer-a-friend.e-mail"/>
                            <img src="${iconUrl}/raf/icon--email.png"
                                 srcset="${iconUrl}/raf/icon--email.png 1x,
                                 ${iconUrl}/raf/icon--email@2x.png 2x" class="em-icon">
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${invitationStatusEnabled}">
            <loy:invitationStatus recommendationsInfo="${recommendationsInfo}"/>
        </c:if>

        <div class="separator-shadow hidden-xs"></div>

        <loy:howToBenefitReferAFriend iconUrl="${iconUrl}"/>

        <div class="separator-shadow inverse hidden-xs"></div>

        <loy:termsAndConditionsReferAFriend/>

    </jsp:body>

</zpbp:loyResponsiveBasicPage>

