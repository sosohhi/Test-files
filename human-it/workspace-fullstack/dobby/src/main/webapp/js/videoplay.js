/**
 * 동영상 상세화면 js
 */
$(document).ready(function() {
    // 사이드바 토글 기능
    $("#toggleButton").click(function() {
        $("#sidebar").toggleClass("hidden");
        $(".allVideo").toggleClass("moved");
    });

    // 서브메뉴 토글 기능
    const navItem = document.querySelector('.nav-item.na');
    const subMenu = document.querySelector('.sub-menu');

    navItem.addEventListener('click', () => {
        subMenu.style.display = subMenu.style.display === 'block' ? 'none' : 'block';
    });

    // 검색 기능
    $('.search button').on('click', function() {
        const searchText = $('.search input').val().toLowerCase();
        $('.video').each(function() {
            const videoTitle = $(this).find('h2').text().toLowerCase();
            if (videoTitle.includes(searchText)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

    // 좋아요 버튼 클릭 기능
    $('#likeButton').click(function() {
        const seq = $(this).data('seq');
        $.post('likes.jsp', { seq: seq }, function(response) {
            alert('Liked!');
            location.reload();
        });
    });
    
});