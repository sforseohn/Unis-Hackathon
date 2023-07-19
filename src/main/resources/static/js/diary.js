document.getElementById('diaryForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var title = document.getElementById('title').value;
    var content = document.getElementById('content').value;
    var userId = localStorage.getItem('userId');
    var date = new Date();
    var dateString = date.toISOString();

    var diaryAnalysisRequest = {
        userId: userId,
        title: title,
        content: content,
        date: dateString
    };

    fetch('http://localhost:8080/diary-analysis', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(diaryAnalysisRequest)
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            alert(data.error);
        } else {
            console.log('Diary created and analysis completed successfully');
            alert('Diary created and analysis completed successfully');
            // Here, handle the analysis result
            return "/diary-analysis";
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
});
