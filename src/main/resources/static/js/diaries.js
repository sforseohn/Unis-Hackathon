var userId = new URLSearchParams(window.location.search).get('userId');

fetch('http://localhost:8080/api/diary/' + userId, {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json'
    }
})
.then(response => response.json())
.then(data => {
    var diaryList = document.getElementById('diaryList');
    data.forEach(diary => {
        var diaryDiv = document.createElement('div');
        diaryDiv.textContent = diary.title + ' - ' + diary.content;
        diaryList.appendChild(diaryDiv);
    });
})
.catch((error) => {
    console.error('Error:', error);
});