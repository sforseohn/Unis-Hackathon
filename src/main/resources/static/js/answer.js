<script>
  var groupButton = document.getElementById("groupButton");
  var answer6 = document.getElementsByClassName("answer6")[0];
  if (groupButton) {
    groupButton.addEventListener("click", function (e) {
      // Save answer to localStorage
      localStorage.setItem('answer6', answer6.value);

      // Fetch all answers from localStorage
      var allAnswers = {
        answer1: localStorage.getItem('answer1'),
        answer2: localStorage.getItem('answer2'),
        answer3: localStorage.getItem('answer3'),
        answer4: localStorage.getItem('answer4'),
        answer5: localStorage.getItem('answer5'),
        answer6: localStorage.getItem('answer6')
      };

      // Send all answers to the server
      fetch("http://localhost:8080/diary-analysis", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(allAnswers)
      })
      .then(response => response.json())
      .then(data => console.log(data))
      .catch((error) => {
        console.error('Error:', error);
      });

       window.location.href = "\result";
    });
  }
</script>
