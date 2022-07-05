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

if (localStorage.getItem('theme') === null) {
    localStorage.setItem('theme', "light")
}
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


if (localStorage.getItem('theme') === "dark") {
    document.body.setAttribute("data-layout-mode", "dark");
} else {
    document.body.removeAttribute("data-layout-mode");
    // }
}

// checkStatus()
//
// function checkStatus(){
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

//
/********************* Websocket (comment service) ************************/
//

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

//
/********************* Custom field  ************************/
//

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

    let fieldId = document.createElement('input');
    fieldId.hidden = true;
    fieldId.value = '';
    fieldId.classList.add("form-control");
    fieldId.id = 'fieldId';

    li.append(fieldId);
    li.append(fieldType);
    li.append(fieldName);
    li.append(removeElement);
    list.append(li)
}

function createCollectionDto() {

    if (!checkValidity()) {
        return;
    }
    // const customFieldNames = document.querySelectorAll('#fieldNameId')
    // const customFieldTypes = document.querySelectorAll('#fieldTypeId')
    // const customFields = {};
    // for (let i = 0; i < customFieldNames.length; i++) {
    //     // customFields['name'] = customFieldNames[i].value;
    //     // customFields['type'] = customFieldTypes[i].value;
    //     customFields[customFieldNames[i].value] = customFieldTypes[i].value;
    // }

    const formListItems = document.getElementsByClassName("list-group-item");
    const customFields = [];
    console.log(formListItems.length);
    for (let i = 0; i < formListItems.length; i++) {
        customFields.push({
            fieldId: formListItems[i].children[0].value,
            fieldType: formListItems[i].children[1].value,
            fieldName: formListItems[i].children[2].value
        })
    }
    let collectionDto = new FormData();
    let id = document.querySelector('#collectionId').value;
    let image = document.querySelector('#collectionImage');
    let title = document.querySelector('#collectionTitle').value
    let description = document.querySelector('#collectionDescription').value
    let topic = document.querySelector('#collectionTopic').value
    let previousImgUrl = document.querySelector('#collectionImgUrl').value

    const body = {id, title, description, topic, previousImgUrl, customFields};

    collectionDto.append('collectionDto', new Blob([JSON.stringify(body)], {
        type: "application/json"
    }))
    collectionDto.append('image', image.files[0])
    saveCollectionDto(collectionDto);
}

function saveCollectionDto(collectionDto) {
    fetch('/collection/save', {
        method: 'POST',
        body: collectionDto,
        redirect: 'follow'
    })
        .then(res => {
            console.log(res);
            location.href = res.url;
        })
        .catch(err => {
            console.log(err)
        })
}

function checkValidity() {
    let title = document.querySelector('#collectionTitle')
    let description = document.querySelector('#collectionDescription')
    let topic = document.querySelector('#collectionTopic')
    const customFieldNames = document.querySelectorAll('#fieldNameId')
    if (title.value.trim().length < 1 || description.value.trim().length < 1 ||
        topic.value === '-1') return false;
    for (let i = 0; i < customFieldNames.length; i++) {
        if (customFieldNames[i].value.trim().length < 1) {
            return false;
        }
    }
    return true;
}

// function searchTags(val) {
//     res = document.getElementById("result");
//     res.innerHTML = '';
//     if (val == '') {
//         return;
//     }
//     let list = '';
//     fetch('/item/tag?value=' + val).then(
//         function (response) {
//             return response.json();
//         }).then(function (data) {
//         for (i = 0; i < data.length; i++) {
//             list += '<li>' + data[i] + '</li>';
//         }
//         res.innerHTML = '<ul>' + list + '</ul>';
//         return true;
//     }).catch(function (err){
//         console.warn('Something went wrong.', err);
//         return false;
//     })
// }

function checkUserAuth(user) {

    $("#loginModal").append(`<div class="modal fade" tabindex="-1" id="myModal"
     aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">
                    Log in to continue
                </h5>
                <button type="button" class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <a href="/login" class="btn btn-primary">Login</a>
                <a href="/register" class="btn btn-outline-primary">Register</a>
            </div>
        </div>
    </div>
</div>
`)

    var myModal = new bootstrap.Modal(document.getElementById('myModal'), {
        keyboard: true
    })
    if (user === 'anonymousUser') {
        console.log("Login first...")
        myModal.show()
        }
}

// function changeLocale() {
//     let locale = document.querySelector('#locales')
//     locale.setAttribute("Onchange", function () {
//         var selectedOption = document.querySelector('#locales').value;
//         if (selectedOption != '') {
//             window.location.replace('international?lang=' + selectedOption);
//         }
//     });
// }

// $(document).ready(
//     function() {
//     $("#locales").change(function () {
//         var selectedOption = $('#locales').val();
//         if (selectedOption != ''){
//             window.location.replace('international?lang=' + selectedOption);
//         }
//     });
// });



