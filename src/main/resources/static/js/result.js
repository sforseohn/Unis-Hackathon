document.addEventListener('DOMContentLoaded', function() {
        var str = localStorage.getItem('analysisId');
        let id = parseInt(str);

        fetch('/diary-analysis/' + id)
            .then(response => response.json())
            .then(data => {
                // 이 부분에서 HTML 요소를 선택하고, 그 내용을 위에서 받은 데이터로 업데이트합니다.
                document.getElementById("comfort").innerText = data.q2Explanation;
                document.getElementById("commonbox").innerText = data.q1Explanation;

                    document.getElementById("q1").addEventListener("click", function() {
                      document.getElementById("commonbox").innerText = data.q1Explanation;
                    });

                    document.getElementById("q3").addEventListener("click", function() {
                      document.getElementById("commonbox").innerText = data.q3Explanation;
                    });

                    document.getElementById("q4").addEventListener("click", function() {
                      document.getElementById("commonbox").innerText = data.q4Explanation;
                    });

                    document.getElementById("q5").addEventListener("click", function() {
                      document.getElementById("commonbox").innerText = data.q5Explanation;
                    });

            });
    });