var stompClient = null;


function windowScroll() {
    const navbar = document.getElementById("navbar");
    if (
        document.body.scrollTop >= 50 ||
        document.documentElement.scrollTop >= 50
    ) {
        navbar.classList.add("nav-sticky");
    } else {
        navbar.classList.remove("nav-sticky");
    }
}

window.addEventListener("scroll", (ev) => {
    ev.preventDefault();
    windowScroll();
});

//
/********************* light-dark js ************************/
//

const btn = document.getElementById("mode");
btn.addEventListener("click", (e) => {
    let theme = localStorage.getItem("theme");
    if (theme == "light" || theme == "") {
        document.body.setAttribute("data-layout-mode", "dark");
        localStorage.setItem("theme", "dark");
    } else {
        document.body.removeAttribute("data-layout-mode");
        localStorage.setItem("theme", "light");
    }
});

//
/********************* Swicher js ************************/
//

function toggleSwitcher() {
    var i = document.getElementById("style-switcher");
    if (i.style.left === "-189px") {
        i.style.left = "-0px";
    } else {
        i.style.left = "-189px";
    }
}

function setColor(theme) {
    document.getElementById("color-opt").href = "./css/colors/" + theme + ".css";
    toggleSwitcher(false);
}

function connectToWebSocket(itemId) {
    getItemComments();
    var socket = new SockJS('/comment');
    console.log('ItemId ',itemId)
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/item/' + itemId + '/item-comments', function (comment) {
            showComments(JSON.parse(comment.body));
        })
    })
}

function showComments(comment) {
    $("#comments").append(`<div class="process-box pt-4">
                                <div class="d-flex align-items-center justify-content-start">
                                    <div class="process-image">
                                        <div class="avatar forum-profile">
                                            <img src="${comment.senderProfileImgUrl}" alt=""
                                                class="img-fluid rounded-circle avatar-md">
                                        </div>
                                    </div>
                                    <div class="profile-content ms-3">
                                        <div class="side-content d-flex">
                                            <h6 class="fw-bold mb-0">${comment.senderFullName}</h6>
                                            <p class="mb-0 ms-4 text-muted"><i
                                                    class="mdi mdi-clock-outline f-18 me-2 align-middle">${comment.commentSentAt}</i>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="process-content">
                                    <p class="text-muted">${comment.body}
                                    </p>
                                    <hr>
                                </div>
                            </div>
`)
}

function leaveComment() {
    let itemId = document.querySelector('#itemId').value;
    let commentBody = document.querySelector('#commentBody');
    const data = JSON.stringify({
        'body': commentBody.value,
        'itemId': itemId
    })
    console.log(stompClient)
    stompClient.send("/app/userComment", {}, data);
    commentBody.value = '';

    // const dataObj = JSON.parse(data);
    // showComments(dataObj)
}

function getItemComments(){
    let itemId = document.querySelector('#itemId').value;
    fetch('/api/item-comments/'+itemId)
        .then(function (res){
            res.json()
                .then(data => {
                    data.map(comment => {
                        showComments(comment)
                    })
                })
        })
}

function addCustomField(){
    const inpType = document.querySelector('.input_type').value;
    let inp = '<div type="text" value="" class="form-control value" contenteditable></div>';
    if (inpType == 'bool') {
        inp = '<input type="checkbox" class="value inputs" id="">Yes';
    } else if (inpType == 'date') {
        inp = '<input class="value inputs" type = "date">';
    }
    const allFields = document.querySelectorAll('#customFields');
    let newField = `<div class="input_group row">
                                    <div class="field_1 col-md-6">
                                    <div type="text" value="" class="form-control key" contenteditable> </div>
                                </div>
                                <div class="field_2 col-md-6">
                                    ${inp}
                                </div>
                            </div>`;
    document.querySelector('.fields_body').insertAdjacentHTML('beforeend', newField);
}

function createCollection(){
    const requestBody = {};
    const keys = document.querySelectorAll('.key');
    const values = document.querySelectorAll('.value');
    const customFields = {};
    for (let i = 0; i <= keys.length; i++) {
        try {
            if (keys[i].innerText && (values[i].innerText || values[i].value)) {
                if (values[i].type == 'checkbox') {
                    customFields[keys[i].innerText] = values[i].checked;
                } else {
                    customFields[keys[i].innerText] = values[i].innerText || values[i].value;
                }
            }
        } catch (error) {

        }
    }
    // if (customFields) requestBody.customFields = customFields;
    console.log(customFields);
    fetch('/api/collection/create',{
        method: 'POST',
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            'title': document.querySelector('#title').value,
            'description': document.querySelector('#description').value,
            'topic': document.querySelector('#topic').value,
            // 'collectionImage':document.querySelector('#collectionImage').files[0],
            'customFields':customFields
        })
    })
        .then(function (res){
            res.json()
                .then(data => {
                    data.map(something => {
                    })
                })
        })
}


