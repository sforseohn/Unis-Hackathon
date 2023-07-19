
userId=localStorage.getItem('userId');
fetch("http://localhost:8080/diary-analysis/" + userId, {
    method: 'GET',
    headers: {
            'Content-Type': 'application/json'
    }
})
.then(response => {
    if (!response.ok) {
      throw new Error("HTTP error " + response.status);
    }
    return response.json();
})
.then(json => {
    if (json.length === 0) {
      console.log("No analyses available for this user.");
      return;
    }

    // Get the last analysis
    let analysis = json[json.length - 1];
    console.log(analysis);

    document.getElementById("q1keywords").innerText = analysis.q1Keywords;
    document.getElementById("q2keywords").innerText = analysis.q2Keywords;
    document.getElementById("q3keywords").innerText = analysis.q3Keywords;
    document.getElementById("q4keywords").innerText = analysis.q4Keywords;

    document.getElementById("keyword1").addEventListener("click", function() {
      document.getElementById("keywordTexts").innerText =  analysis.q1Keywords;
      document.getElementById("explanation").innerText = analysis.q1Explanation;
    });

    document.getElementById("keyword2").addEventListener("click", function() {
      document.getElementById("keywordTexts").innerText = analysis.q2Keywords;
      document.getElementById("explanation").innerText = analysis.q2Explanation;
    });

    document.getElementById("keyword3").addEventListener("click", function() {
      document.getElementById("keywordTexts").innerText = analysis.q3Keywords;
      document.getElementById("explanation").innerText = analysis.q3Explanation;
    });

    document.getElementById("keyword4").addEventListener("click", function() {
      document.getElementById("keywordTexts").innerText = analysis.q4Keywords;
      document.getElementById("explanation").innerText = analysis.q4Explanation;
    });
})
.catch(function() {
    console.log("An error occurred while fetching the analysis.");
});

