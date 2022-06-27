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
    console.log('ItemId ', itemId)
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

function getItemComments() {
    let itemId = document.querySelector('#itemId').value;
    fetch('/api/item-comments/' + itemId)
        .then(function (res) {
            res.json()
                .then(data => {
                    data.map(comment => {
                        showComments(comment)
                    })
                })
        })
}

async function createCollectionDto() {
    const customFieldNames = document.querySelectorAll('#fieldNameId')
    const customFieldTypes = document.querySelectorAll('#fieldTypeId')
    const customFields = {};
    for (let i = 0; i < customFieldNames.length; i++) {
        customFields[customFieldNames[i].value] = customFieldTypes[i].value;
    }
    let collectionDto = new FormData();
    let image = document.querySelector('#collectionImage');
    let title = document.querySelector('#title').value
    let description = document.querySelector('#description').value
    let topic = document.querySelector('#topic').value

    const body = {title, description, topic, customFields};

    collectionDto.append('collectionDto', new Blob([JSON.stringify(body)], {
        type: "application/json"
    }))
    collectionDto.append('image', image.files[0])

    await fetch('/collection/create', {
        method: 'POST',
        body: collectionDto
    })
}

function addCustomField() {

    const list = document.querySelector('.fields_body');

    let li = document.createElement('li');

    li.classList.add("list-group-item");
    li.name = "fieldListItems"

    var fieldType = document.createElement('select');
    fieldType.classList.add("form-select");
    fieldType.id = 'fieldTypeId'

    // const dataTypesCustomField = document.querySelector('#dataTypesCustomField')
    //
    // for (let [key,value] of dataTypesCustomField){
    //     fieldType.add(new Option(value,key))
    // }

    fieldType.add(new Option("String field", "text"))
    fieldType.add(new Option("Multi line text field", "textarea"))
    fieldType.add(new Option("Boolean checkbox field", "checkbox"))
    fieldType.add(new Option("Integer field", "number"))
    fieldType.add(new Option("Date field", "date"))

    let fieldName = document.createElement('input');
    fieldName.required = true
    fieldName.classList.add("form-control");
    fieldName.id = 'fieldNameId';
    fieldName.placeholder = "Enter field name";

    let removeElement = document.createElement('button');
    removeElement.innerHTML = '-'
    removeElement.classList.add("file__remove");

    removeElement.addEventListener("click", function () {
        li.remove();
    });

    li.append(fieldType);
    li.append(fieldName);
    li.append(removeElement);
    list.append(li)
}


