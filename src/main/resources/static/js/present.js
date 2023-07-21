document.addEventListener('DOMContentLoaded', function() {
    var str = localStorage.getItem('analysisId');
    let id = parseInt(str);

    fetch('/api/present/' + id)
        .then(response => response.json())
        .then(data => {
            console.log(data);

            data.youtubeUrl.forEach((videoLink, index) => {
                    const videoId = getYouTubeId(videoLink);
                    const thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";

                    // Create an image element for the thumbnail
                    const img = document.createElement("img");
                    img.src = thumbnailUrl;
                    img.alt = "Video thumbnail";

                    // Add the image and video link to the anchor element
                    const anchor = document.getElementById('video' + (index + 1));
                    anchor.href = videoLink;
                    anchor.target = "_blank";
                    anchor.appendChild(img);

            });
        });
});

function getYouTubeId(url) {
  var id = '';
  url = url.replace(/(>|<)/gi,'').split(/(vi\/|v=|\/v\/|youtu\.be\/|\/embed\/)/);
  if(url[2] !== undefined) {
    id = url[2].split(/[^0-9a-z_\-]/i);
    id = id[0];
  }
  else {
    id = url;
  }
  return id;
}


