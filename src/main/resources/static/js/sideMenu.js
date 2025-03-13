
function menuClick() {
    const $menuList = $('.menu-list');
    const currentUrl = window.location.pathname;
    const urlSlice = currentUrl.split('/').slice(0,3).join('/');
    console.log("urlSlice : ", urlSlice);
    $menuList.on('click', function () {
        window.location.href = $(this).data('url');
        console.log("click")
    })

    $menuList.each(function () {
        const menuUrl = $(this).data('url');
        const menuUrlSlice = menuUrl.split('/').slice(0,3).join('/');
        console.log("menuSlice", menuUrlSlice);
        if(urlSlice === menuUrlSlice) {
            $(this).addClass('menu-active');
            console.log("일치함")
        }else {
            $(this).removeClass('menu-active');
            console.log("일치하지 않음")
        }
    })

}



