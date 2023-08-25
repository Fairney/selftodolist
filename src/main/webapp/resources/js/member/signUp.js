const signUpValue = {
    "memberId"     : false,
    "memberPw"        : false,
    "memberPwConfirm" : false,
    "memberNick"      : false,
    "memberEmail"     : false,
    "memberAddr"      : false,
    "agreeChecked"    : false
}
const member = document.getElementById("memberIdInput");
const idMessage = document.getElementById("id_desc");
const memberPwInput = document.getElementById("memberPw");
const pwMessage = document.getElementById("pw_desc");
const pwConfmMessage = document.getElementById("pwfm_desc");
const memberPwConfmInput = document.getElementById("memberPwConfm");
const memberNickInput = document.getElementById("memberNick");
const nickMessage = document.getElementById("nick_desc");
const memberEmailInput = document.getElementById("memberEmail");
const emailMessage = document.getElementById("email_desc");
const allCheckBox = document.getElementById("chk_all");
const emailOptInCheckBox = document.getElementById("termsService4");
const blindList = document.getElementsByClassName("blind");

member.addEventListener("change",function(){
    // 입력되지 않은 경우
    if(member.value.length == 0){
        idMessage.innerText = "영어(대문자 또는 소문자), 숫자, 또는 한글 문자 2~10글자로 이루어져야 합니다.";
        idMessage.classList.remove("confirm", "error");
        idMessage.style.color = "black";
        signUpValue.memberId = false; // 유효 X 기록
        return;
    }

    const regExp = /^[a-zA-Z0-9가-힣]{2,10}$/;

    if( regExp.test(member.value) ){ // 유효한 경우
        

        // ****** 닉네임 중복 검사(ajax) 진행 예정 ******

        $.ajax({
            url : contextPath +"/member/idDupCheck",  // 필수 작성 속성
            data : { "memberId" : member.value }, // 서버로 전달할 값(파라미터)
            type : "GET", // 데이터 전달 방식(기본값 GET)

            success : function(res){ // 비동기 통신 성공 시(에러 발생 X)

                // 매개변수 res : Servlet에서 응답으로 출력된 데이터가 저장

                if(res == 0){ // 닉네임 중복 X
                    idMessage.innerText = "사용 가능한 닉네임 입니다.";
                    idMessage.classList.add("confirm");
                    idMessage.classList.remove("error");
                    idMessage.style.color = "green";
                    signUpValue.memberId = true; // 유효 O 기록

                } else { // 닉네임 중복 O
                    idMessage.innerText = "이미 사용중인 닉네임 입니다.";
                    idMessage.classList.add("error");
                    idMessage.classList.remove("confirm");
                    idMessage.style.color = "red";
                    signUpValue.memberId = false; // 유효 O 기록
                }
            },

            error : function(){ // 비동기 통신 중 에러가 발생한 경우
                console.log("에러 발생");
            }
        });



    }else{
        idMessage.innerText = "닉네임 형식이 유효하지 않습니다.";
        idMessage.classList.add("error");
        idMessage.classList.remove("confirm");
        idMessage.style.fontSize='13px';
        idMessage.style.color = "red";

        signUpValue.memberNick = false; // 유효 X 기록
    }

});


memberPwConfmInput.addEventListener("focus",function(){
    if(memberPw.value.length == 0){
        alert("비밀번호를 먼저 입력해주세요.");
        memberPwInput.focus();
        return;
    }
})

memberPwInput.addEventListener("input",function(){
    // 입력되지 않은 경우
    if(memberPwInput.value.length == 0){
        pwMessage.innerText = "영어(대문자 또는 소문자), 숫자, 또는 한글 문자 6~12글자로 이루어져야 합니다.";
        pwMessage.classList.remove("confirm", "error");
        pwMessage.style.color = "black";

        signUpValue.memberId = false; // 유효 X 기록
        return;
    }

    const regExp = /^[a-zA-Z0-9가-힣]{6,12}$/;

    if( !regExp.test(memberPw.value) ){ // 불유효한 경우
        pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");

        signUpValue.memberPw = false; // 유효 X 기록
    }else{
        pwMessage.innerText = "비밀번호 형식이 유효합니다.";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        signUpValue.memberPw = true; // 유효 X 기록
        
    }

});
memberPwConfmInput.addEventListener("input",function(){
    if(memberPwConfmInput.value.length == 0){
        pwConfmMessage.innerText = "영어(대문자 또는 소문자), 숫자, 또는 한글 문자 6~12글자로 이루어져야 합니다.";
        pwConfmMessage.classList.remove("confirm", "error");
        pwConfmMessage.style.color = "black";

        signUpValue.memberPwConfirm = false; // 유효 X 기록
        return;
    }
    if(memberPwInput.value == memberPwConfmInput.value){
        pwConfmMessage.innerText = "비밀번호와 일치합니다.";
        pwConfmMessage.classList.remove("confirm", "error");
        pwConfmMessage.style.color = "black";

        signUpValue.memberPwConfirm = true; // 유효 X 기록
    }else{
        pwConfmMessage.innerText = "비밀번호와 일치하지 않습니다.";
        pwConfmMessage.classList.remove("confirm", "error");
        pwConfmMessage.style.color = "red";

        signUpValue.memberPwConfirm = false; // 유효 X 기록
    }
    
});

memberNickInput.addEventListener("input",function(){
    // 입력되지 않은 경우
    if(memberNickInput.value.length == 0){
        nickMessage.classList.remove("confirm", "error");
        nickMessage.style.color = "black";

        signUpValue.memberNick = false; // 유효 X 기록
        return;
    }

    const regExp = /^[a-zA-Z0-9가-힣]{2,10}$/;

    if( regExp.test(memberNickInput.value) ){ // 유효한 경우
        

        // ****** 닉네임 중복 검사(ajax) 진행 예정 ******

        $.ajax({
            url : contextPath +"/member/nickDupCheck",  // 필수 작성 속성
            data : { "memberNick" : memberNickInput.value }, // 서버로 전달할 값(파라미터)
            type : "GET", // 데이터 전달 방식(기본값 GET)

            success : function(res){ // 비동기 통신 성공 시(에러 발생 X)

                // 매개변수 res : Servlet에서 응답으로 출력된 데이터가 저장

                if(res == 0){ // 닉네임 중복 X
                    nickMessage.innerText = "사용 가능한 닉네임 입니다.";
                    nickMessage.classList.add("confirm");
                    nickMessage.classList.remove("error");
                    nickMessage.style.color = "green";
                    signUpValue.memberNick = true; // 유효 O 기록

                } else { // 닉네임 중복 O
                    nickMessage.innerText = "이미 사용중인 닉네임 입니다.";
                    nickMessage.classList.add("error");
                    nickMessage.classList.remove("confirm");
                    nickMessage.style.color = "red";
                    signUpValue.memberNick = false; // 유효 O 기록
                }
            },

            error : function(){ // 비동기 통신 중 에러가 발생한 경우
                console.log("에러 발생");
            }
        });



    }else{
        nickMessage.innerText = "닉네임 형식이 유효하지 않습니다.";
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");

        signUpValue.memberNick = false; // 유효 X 기록
    }

});

memberEmailInput.addEventListener("input",function(){
    // 입력되지 않은 경우
    if(memberEmailInput.value.length == 0){
        emailMessage.innerText = "영어(대문자 또는 소문자), 숫자, 또는 한글 문자 2~10글자로 이루어져야 합니다.";
        emailMessage.classList.remove("confirm", "error");

        signUpValue.memberId = false; // 유효 X 기록
        return;
    }

    const regExp = /^[\w\-\_]{4,}@[\w\-\_]+(\.\w+){1,3}$/;

    if( regExp.test(memberEmailInput.value) ){ // 유효한 경우
        

        // ****** 닉네임 중복 검사(ajax) 진행 예정 ******

        $.ajax({
            url : contextPath +"/member/emailDupCheck",  // 필수 작성 속성
            data : { "memberEmail" : memberEmailInput.value }, // 서버로 전달할 값(파라미터)
            type : "GET", // 데이터 전달 방식(기본값 GET)

            success : function(res){ // 비동기 통신 성공 시(에러 발생 X)

                // 매개변수 res : Servlet에서 응답으로 출력된 데이터가 저장

                if(res == 0){ // 닉네임 중복 X
                    emailMessage.innerText = "유효한 email입니다.";
                    emailMessage.classList.add("confirm");
                    emailMessage.classList.remove("error");
                    emailMessage.style.color = "green";
                    signUpValue.memberEmail = true; // 유효 O 기록

                } else { // 닉네임 중복 O
                    emailMessage.innerText = "이미 등록된 email입니다.";
                    emailMessage.classList.add("error");
                    emailMessage.classList.remove("confirm");
                    emailMessage.style.color = "red";
                    signUpValue.memberEmail = false; // 유효 O 기록
                }
            },

            error : function(){ // 비동기 통신 중 에러가 발생한 경우
                console.log("에러 발생");
            }
        });



    }else{
        
        emailMessage.innerText = "이메일 형식이 유효하지 않습니다.";
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");
        emailMessage.style.fontSize='13px';
        emailMessage.style.color = "red";

        signUpValue.memberEmail = false; // 유효 X 기록
    }

});

//주소(필수)-우편번호,도로명주소
const memberAddr = Array.from(document.getElementsByName("memberAddr"));

memberAddr.forEach(function (input) {
  input.addEventListener("input", function () {
    // 입력이 되지 않은 경우
    if (memberAddr[0].value.trim().length === 0 || memberAddr[1].value.trim().length === 0) {
        signUpValue.memberAddr = false;
      return;
    }

    signUpValue.memberAddr = true;
  });
});

allCheckBox.addEventListener("change", function() {
    const isChecked = allCheckBox.checked;
  
    for (let i = 0; i < blindList.length; i++) {
      blindList[i].checked = isChecked; // 모두 체크박스들의 체크 상태를 allCheckBox의 체크 여부와 동일하게 설정합니다.
    }
  
    console.log(emailOptInCheckBox.checked)
  });
  
  // 모든 체크박스(blindList)가 눌러지면 allCheckBox가 체크됨/해제됨
  for (let i = 0; i < blindList.length; i++) {
    blindList[i].addEventListener("change", function () {
      let allChecked = true;
      for (let j = 0; j < blindList.length; j++) {
        if (!blindList[j].checked) {
          allChecked = false;
          break;
        }
      }
      allCheckBox.checked = allChecked;
    });
}

const checkboxes = document.getElementsByClassName("neCe");
    


function invalidate(){
    // if(!signUpValue.memberId){
    //     alert("아이디를 입력하세요");
    //     member.focus();
    //     return false;
    // }
    // if(!signUpValue.memberPw){
    //     alert("비밀번호를 입력하세요");
    //     memberPwInput.focus();
    //     return false;
    // }
    // if(!signUpValue.memberPwConfirm){
    //     alert("비밀번호 확인을 입력하세요");
    //     memberPwConfmInput.focus();
    //     return false;
    // }
    // if(!signUpValue.memberNick){
    //     alert("닉네임을 입력하세요.");
    //     memberNickInput.focus();
    //     return false;
    // }
    // if(!signUpValue.memberAddr){
    //     alert("주소를 입력해주세요.");
    //     return false;
    // }

    // let allChecked = true;

    // for (let i = 0; i < checkboxes.length; i++) {
    // if (!checkboxes[i].checked) {
    //   allChecked = false;
    //   break;
    // }else{
    //     signUpValue.agreeChecked = true;
    //     break;
    // }
    let str;
    for (let i = 0; i < checkboxes.length; i++) {
        if (!checkboxes[i].checked) {
          allChecked = false;
          signUpValue.agreeChecked = false;
          alert("안돼");
          console.log(checkboxes);
          break;
        }else{
            signUpValue.agreeChecked = true;
            break;
        }
    }
    for( let key  in signUpValue ){ // 객체용 향상된 for문

        // 현재 접근 중인 key의 value가 false인 경우
        if( !signUpValue[key] ){ 

            switch(key){
            case "memberId": str="아이디가"; break;
            case "memberEmail":     str="이메일이"; break;
            case "memberPw":        str="비밀번호가"; break; 
            case "memberAddr":        str="주소가";break;
            case "memberPwConfirm": str="비밀번호 확인이"; break;
            case "memberNick":  str="닉네임이"; break;
            case "agreeChecked":  str="동의 항목이"; break;

            }

            str += " 유효하지 않습니다.";

            alert(str);

            //document.getElementById(key).focus();
            
            return false; // form태그 기본 이벤트 제거
        }
    }

    return true; // form태그 기본 이벤트 수행


}


