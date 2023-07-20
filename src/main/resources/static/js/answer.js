// groupButton1 이벤트 리스너를 추가합니다.
var groupButton1 = document.getElementById("groupButton1");
if (groupButton1) {
  groupButton1.addEventListener("click", function (e) {
  window.location.href = '/q5';
});
}

document.getElementById("q6Form").addEventListener("submit", function (e) {
        e.preventDefault();
        var answer6 = document.querySelector('.answer6').value;

        localStorage.setItem('answer6', answer6);

            // 각각의 답변을 콘솔에 출력합니다.
            console.log(localStorage.getItem('answer1'));
            console.log(localStorage.getItem('answer2'));
            console.log(localStorage.getItem('answer3'));
            console.log(localStorage.getItem('answer4'));
            console.log(localStorage.getItem('answer5'));
            console.log(localStorage.getItem('answer6'));

            var userId = localStorage.getItem('userId');

            // localStorage에서 모든 답변을 가져옵니다.
            var allAnswers = {
              userId: userId,
              answer1: localStorage.getItem('answer1'),
              answer2: localStorage.getItem('answer2'),
              answer3: localStorage.getItem('answer3'),
              answer4: localStorage.getItem('answer4'),
              answer5: localStorage.getItem('answer5'),
              answer6: localStorage.getItem('answer6')
            };

            // 모든 답변을 서버로 보냅니다.
            fetch("http://localhost:8080/diary-analysis", {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(allAnswers)
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                localStorage.setItem('analysisId', data.id);
                // '/result' 페이지로 이동합니다.
                window.location.href = '/result';
            })
            .catch((error) => {
              console.error('Error:', error);
            });
        });


