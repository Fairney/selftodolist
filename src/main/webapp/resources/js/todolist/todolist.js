window.onload = function () { buildCalendar(); }    // 웹 페이지가 로드되면 buildCalendar 실행
const todayList = document.getElementById("todayList");
const weekList = document.getElementById("weekList");


        let nowMonth = new Date();  // 현재 달을 페이지를 로드한 날의 달로 초기화
        let today = new Date();     // 페이지를 로드한 날짜를 저장
        today.setHours(0,0,0,0);    // 비교 편의를 위해 today의 시간을 초기화

        // 달력 생성 : 해당 달에 맞춰 테이블을 만들고, 날짜를 채워 넣는다.
        function buildCalendar() {

            let firstDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), 1);     // 이번달 1일
            let lastDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, 0);  // 이번달 마지막날

            let tbody_Calendar = document.querySelector(".Calendar > tbody");
            document.getElementById("calYear").innerText = nowMonth.getFullYear();             // 연도 숫자 갱신
            document.getElementById("calMonth").innerText = leftPad(nowMonth.getMonth() + 1);  // 월 숫자 갱신

            while (tbody_Calendar.rows.length > 0) {                        // 이전 출력결과가 남아있는 경우 초기화
                tbody_Calendar.deleteRow(tbody_Calendar.rows.length - 1);
            }

            let nowRow = tbody_Calendar.insertRow();        // 첫번째 행 추가           

            for (let j = 0; j < firstDate.getDay(); j++) {  // 이번달 1일의 요일만큼
                let nowColumn = nowRow.insertCell();        // 열 추가
            }

            for (let nowDay = firstDate; nowDay <= lastDate; nowDay.setDate(nowDay.getDate() + 1)) {   // day는 날짜를 저장하는 변수, 이번달 마지막날까지 증가시키며 반복  

                let nowColumn = nowRow.insertCell();        // 새 열을 추가하고
                nowColumn.innerText = leftPad(nowDay.getDate());      // 추가한 열에 날짜 입력

            
                if (nowDay.getDay() == 0) {                 // 일요일인 경우 글자색 빨강으로
                    nowColumn.style.color = "#DC143C";
                }
                if (nowDay.getDay() == 6) {                 // 토요일인 경우 글자색 파랑으로 하고
                    nowColumn.style.color = "#0000CD";
                    nowRow = tbody_Calendar.insertRow();    // 새로운 행 추가
                }


                if (nowDay < today) {                       // 지난날인 경우
                    nowColumn.className = "pastDay";
                }
                else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == today.getDate()) { // 오늘인 경우           
                    nowColumn.className = "today";
                    nowColumn.onclick = function () { choiceDate(this); }
                }
                else {                                      // 미래인 경우
                    nowColumn.className = "futureDay";
                    nowColumn.onclick = function () { choiceDate(this); }
                }
            }
        }

        // 날짜 선택
        function choiceDate(nowColumn) {
            if (document.getElementsByClassName("choiceDay")[0]) {                              // 기존에 선택한 날짜가 있으면
                document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");  // 해당 날짜의 "choiceDay" class 제거
            }
            nowColumn.classList.add("choiceDay");           // 선택된 날짜에 "choiceDay" class 추가
            //ajax를 추가해서 해당 날짜의 내용을 가져와야한다. 가져와서 안에 내용을 집어넣어야함.
          
            $.ajax({
                url:contextPath + "/todolist/choiceDate",
                type:"GET",
                dataType: "json",
                data: {"date" : nowColumn.innerText,
                       "year" : nowMonth.getFullYear(),
                       "month" : nowMonth.getMonth() + 1},
                success : function(result){
                    selectList(result);
                },
                error:function(result){
                    console.log(result);
                    console.log("실패");
                }
            });

        }
        
        // 이전달 버튼 클릭
        function prevCalendar() {
            nowMonth = new Date(nowMonth.getFullYear(), nowMonth.getMonth() - 1, nowMonth.getDate());   // 현재 달을 1 감소
            buildCalendar();    // 달력 다시 생성
        }
        // 다음달 버튼 클릭
        function nextCalendar() {
            nowMonth = new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, nowMonth.getDate());   // 현재 달을 1 증가
            buildCalendar();    // 달력 다시 생성
        }

        // input값이 한자리 숫자인 경우 앞에 '0' 붙혀주는 함수
        function leftPad(value) {
            if (value < 10) {
                value = "0" + value;
                return value;
            }
            return value;
        }
    
        const todayDate = document.getElementById("todayDate");
        const todayYear = document.getElementById("todayYear");
        const todayMonth = document.getElementById("todayMonth");
function openPopup() {
    let choiceDate= document.getElementsByClassName("choiceDay");
    if (choiceDate.length === 0) {
        choiceDate = document.getElementsByClassName("today");
        console.log("안됨");
    }
    var popup = document.getElementById("popup");
    popup.style.visibility = "visible";
    popup.style.opacity = "1";
    todayDate.value = choiceDate[0].innerText;
    todayYear.value = nowMonth.getFullYear();
    todayMonth.value = nowMonth.getMonth() + 1;
    console.log(todayDate.value);
}

function closePopup() {
    var popup = document.getElementById("popup");
    popup.style.visibility = "hidden";
    popup.style.opacity = "0";
    popup.style.zIndex="1";
}

const inputDate = document.getElementById("weekDate");
const inputYear = document.getElementById("weekYear");
const inputMonth = document.getElementById("weekMonth");

function openPopup2() {
    let choiceDate= document.getElementsByClassName("choiceDay");
    if (choiceDate.length === 0) {
        choiceDate = document.getElementsByClassName("today");
        
    }
    var introPopup = document.getElementById("introPopup");
    introPopup.style.visibility = "visible";
    introPopup.style.opacity = "1";
    introPopup.style.zIndex = "0";
    inputDate.value = choiceDate[0].innerText;
    inputYear.value = nowMonth.getFullYear();
    inputMonth.value = nowMonth.getMonth() + 1;
    
}

function closePopup2() {
    var introPopup = document.getElementById("introPopup");
    introPopup.style.visibility = "hidden";
    introPopup.style.opacity = "0";
}

function letterValidate() {
    let valueList = {
        letterTitle: false,
        letterWriter: false,
        letterSender: false,
        letterText: false
    }
    const writerName = document.getElementsByName("writerName");
    const senderName = document.getElementsByName("sendName");
    const titleValue = document.getElementsByName("sendTitle");
    const sendText = document.getElementsByName("sendText");

   

    if (sendText[0].value.length != 0) {
        valueList.letterText = true;
    } else {
        alert("내용을 입력하세요.");
        valueList.letterText = false;
    }

    if (Object.values(valueList).reduce((acc, val) => acc + val, 0) === 4) {
        return true;
    } else {
        return false;
    }
}
const btn_checkContent = document.getElementById("btn-checkContent");

function deleteTodayCheckContent(){
    // 선택된 목록 가져오기
    const query = 'input[name="todayContent"]:checked';
    const selectedEls = 
        document.querySelectorAll(query);
    
    // 선택된 목록에서 value 찾기
    let result = '';
    selectedEls.forEach((el) => {
      result += el.value + ',';
    });
    
    let choiceDate= document.getElementsByClassName("choiceDay");
    if (choiceDate.length === 0) {
        choiceDate = document.getElementsByClassName("today");
        console.log("안됨");
    }
    $.ajax({
        url:contextPath + "/todolist/selectTodayTodolist",
        type:"POST",
        dataType: "json",
        data: {"deleteList" : result,
               "date" : choiceDate[0].innerText,
               "year" : nowMonth.getFullYear(),
               "month" : nowMonth.getMonth() + 1},
        success:function(result){
            selectList(result);
        },
        error:function(result){
            console.log("실패");
        }
    });
}
function deleteCheckContent()  {
    // 선택된 목록 가져오기
    const query = 'input[name="checkContent"]:checked';
    const selectedEls = 
        document.querySelectorAll(query);
    
    // 선택된 목록에서 value 찾기
    let result = '';
    selectedEls.forEach((el) => {
      result += el.value + ',';
    });
    console.log(result);
    let choiceDate = document.getElementsByClassName("choiceDay");

// If no elements with class name "choiceDay" were found, try selecting elements with class name "today"
if (choiceDate.length === 0) {
    choiceDate = document.getElementsByClassName("today");
    console.log("안됨");
}
    console.log(choiceDate[0].innerText);
    
    $.ajax({
        url:contextPath + "/todolist/selectTodolist",
        type:"POST",
        dataType: "json",
        data: {"deleteList" : result,
               "date" : choiceDate[0].innerText,
               "year" : nowMonth.getFullYear(),
               "month" : nowMonth.getMonth() + 1},
        success:function(result){
            console.log(result.todayList);
            selectList(result);
        },
        error:function(result){
            console.log("실패");
        }
    });
}

function selectList(result){
    console.log(result);
                    weekList.innerHTML ="";
                    todayList.innerHTML = "";
                    if(result.todayList.length != 0){
                    for(let i = 0; i<result.todayList.length; i++){
                        //체크박스 추가
                        const checkbox_area = document.createElement("div");
                        const inputCheckBox = document.createElement("input");
                        inputCheckBox.type = "checkbox";
                        checkbox_area.append(inputCheckBox);
                        //li추가
                        const li_area = document.createElement("div");
                        const liElement = document.createElement("li");
                        liElement.innerText = result.todayList[i].todolistContent;
                        li_area.append(liElement);
                        
                        const btn_area = document.createElement("div");
                        btn_area.classList.add("btn-Field");
                        const btn_element = document.createElement("button");
                        btn_element.innerText = "삭제하기";
                        btn_area.append(btn_element);
                        const plan_mainDiv = document.createElement("div");
                        plan_mainDiv.classList.add("plan-mainText");

                        plan_mainDiv.append(checkbox_area);
                        plan_mainDiv.append(li_area);
                        plan_mainDiv.append(btn_area);
                        todayList.append(plan_mainDiv);

                    
                    }
                    }else{
 
                        const div1 = document.createElement("div");
                        div1.classList.add("no-list");
                        let span1 = document.createElement("span");
                        span1.innerText = "No List";
                        div1.append(span1);
                        todayList.append(div1);
                    }
                    //weekend 추가.
                    if(result.weekList.length != 0){
                    for(let i = 0; i<result.weekList.length; i++){
                        //체크박스 추가
                        const checkbox_area = document.createElement("div");
                        const inputCheckBox = document.createElement("input");
                        inputCheckBox.type = "checkbox";
                        checkbox_area.append(inputCheckBox);
                        //li추가
                        const li_area = document.createElement("div");
                        const liElement = document.createElement("li");
                        liElement.innerText = result.weekList[i].todolistContent;
                        li_area.append(liElement);
                        
                        const btn_area = document.createElement("div");
                        btn_area.classList.add("btn-Field");
                        const btn_element = document.createElement("button");
                        btn_element.innerText = "삭제하기";
                        btn_area.append(btn_element);
    
                        const plan_mainDiv = document.createElement("div");
                        plan_mainDiv.classList.add("plan-mainText");

                        plan_mainDiv.append(checkbox_area);
                        plan_mainDiv.append(li_area);
                        plan_mainDiv.append(btn_area);
                        weekList.append(plan_mainDiv);
                    }
                }else{
 
                    const div1 = document.createElement("div");
                    div1.classList.add("no-list");
                    let span1 = document.createElement("span");
                    span1.innerText = "No List";
                    div1.append(span1);
                    weekList.append(div1);
                }

}