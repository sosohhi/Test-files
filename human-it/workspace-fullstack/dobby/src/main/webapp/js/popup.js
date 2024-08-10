document.querySelectorAll('.each-mv').forEach(item => {
    item.addEventListener('click', () => {
        const videoId = item.getAttribute('data-video-id');
        console.log(`Video ID: ${videoId}`); // ID 확인
        const videoUrl = `https://www.youtube.com/embed/${videoId}`;
        // const videoUrl = `https://www.youtube.com/embed/${videoId}?autoplay=1`
        openPopup(videoUrl);
    });

     // 미리보기 재생
     item.addEventListener('mouseover', () => {
        const videoId = item.getAttribute('data-video-id');
        const previewUrl = `https://www.youtube.com/embed/${videoId}?autoplay=1&mute=1&controls=0`;
        item.querySelector('.preview-img').src = `https://img.youtube.com/vi/${videoId}/maxresdefault.jpg`;
        // 미리보기 동영상 자동 재생을 위한 URL 설정 (YouTube에서는 동영상 자동 재생이 제한적입니다)
        item.dataset.previewUrl = previewUrl;
    });

    // item.addEventListener('mouseout', () => {
    //     item.querySelector('.preview-img').src = ''; // 마우스가 이미지에서 벗어나면 이미지 비우기
    // });
});



function openPopup(videoUrl) {
    const popup = document.getElementById('videoPopup');
    const videoFrame = document.getElementById('videoFrame');
    
    console.log(`Opening video URL: ${videoUrl}`); // URL 확인
    videoFrame.src = videoUrl;
    popup.classList.add('active');
}

document.getElementById('videoPopup').addEventListener('click', function() {
    this.classList.remove('active');
    document.getElementById('videoFrame').src = ''; // 동영상 중지
    console.log('Popup closed'); // 팝업 닫힘 확인
});