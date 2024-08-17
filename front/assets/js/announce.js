document.addEventListener('DOMContentLoaded', function () {
    const notices = [
        { title: '공지사항 제목 1', date: '2024-08-01', content: '공지사항 내용 1' },
        { title: '공지사항 제목 2', date: '2024-08-02', content: '공지사항 내용 2' },
        { title: '공지사항 제목 3', date: '2024-08-03', content: '공지사항 내용 3' },
        { title: '공지사항 제목 4', date: '2024-08-04', content: '공지사항 내용 4' },
        // 추가 공지사항 데이터
    ];

    const noticeList = document.getElementById('notice-list');
    const searchInput = document.getElementById('search');
    const searchButton = document.getElementById('search-button');
    const noticeForm = document.getElementById('notice-form');
    const editNoticeForm = document.getElementById('edit-notice-form');
    const pagination = document.getElementById('pagination');
    const deleteConfirmButton = document.getElementById('confirm-delete');
    let currentPage = 1;
    const noticesPerPage = 2;
    let editingIndex = null;

    function renderNotices(filteredNotices, page) {
        noticeList.innerHTML = '';
        const start = (page - 1) * noticesPerPage;
        const end = start + noticesPerPage;
        const paginatedNotices = filteredNotices.slice(start, end);

        paginatedNotices.forEach((notice, index) => {
            const noticeItem = document.createElement('div');
            noticeItem.className = 'list-group-item list-group-item-action notice-item';
            noticeItem.innerHTML = `
                <h5 class="notice-title" data-toggle="collapse" data-target="#collapse${start + index}" aria-expanded="false" aria-controls="collapse${start + index}">
                    ${notice.title}
                </h5>
                <div id="collapse${start + index}" class="collapse">
                    <p class="notice-date">${notice.date}</p>
                    <p>${notice.content}</p>
                    <button class="btn btn-warning btn-sm mr-2 edit-button" data-index="${start + index}">수정</button>
                    <button class="btn btn-danger btn-sm delete-button" data-index="${start + index}">삭제</button>
                </div>
            `;
            noticeList.appendChild(noticeItem);
        });

        renderPagination(filteredNotices.length, page);
    }

    function renderPagination(totalNotices, page) {
        pagination.innerHTML = '';
        const totalPages = Math.ceil(totalNotices / noticesPerPage);

        for (let i = 1; i <= totalPages; i++) {
            const pageItem = document.createElement('li');
            pageItem.className = `page-item ${i === page ? 'active' : ''}`;
            pageItem.innerHTML = `<a class="page-link" href="#" data-page="${i}">${i}</a>`;
            pageItem.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = i;
                renderNotices(notices, currentPage);
            });
            pagination.appendChild(pageItem);
        }
    }

    function openEditModal(index) {
        const notice = notices[index];
        document.getElementById('edit-index').value = index;
        document.getElementById('edit-notice-title').value = notice.title;
        document.getElementById('edit-notice-date').value = notice.date;
        document.getElementById('edit-notice-content').value = notice.content;
        $('#editNoticeModal').modal('show');
    }

    function deleteNotice(index) {
        notices.splice(index, 1);
        $('#deleteConfirmModal').modal('hide');
        renderNotices(notices, currentPage);
    }

    searchButton.addEventListener('click', function () {
        const searchTerm = searchInput.value.toLowerCase();
        const filteredNotices = notices
            .filter(notice => notice.title.toLowerCase().includes(searchTerm))
            .sort((a, b) => new Date(b.date) - new Date(a.date)); // 내림차순 정렬
        renderNotices(filteredNotices, 1); // 첫 페이지를 렌더링
    });

    noticeForm.addEventListener('submit', function (event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지

        const title = document.getElementById('notice-title').value;
        const date = document.getElementById('notice-date').value;
        const content = document.getElementById('notice-content').value;

        // 새 공지사항 객체 생성
        const newNotice = {
            title: title,
            date: date,
            content: content
        };

        // 공지사항 배열에 새 공지사항 추가
        notices.push(newNotice);

        // 모달 숨기기
        $('#addNoticeModal').modal('hide');

        // 폼 필드 초기화
        noticeForm.reset();

        // 공지사항 목록 업데이트
        renderNotices(notices.sort((a, b) => new Date(b.date) - new Date(a.date)), 1); // 첫 페이지를 렌더링
    });

    editNoticeForm.addEventListener('submit', function (event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지

        const index = document.getElementById('edit-index').value;
        const title = document.getElementById('edit-notice-title').value;
        const date = document.getElementById('edit-notice-date').value;
        const content = document.getElementById('edit-notice-content').value;

        // 수정된 공지사항 객체 업데이트
        notices[index] = { title: title, date: date, content: content };

        // 모달 숨기기
        $('#editNoticeModal').modal('hide');

        // 폼 필드 초기화
        editNoticeForm.reset();

        // 공지사항 목록 업데이트
        renderNotices(notices.sort((a, b) => new Date(b.date) - new Date(a.date)), currentPage); // 현재 페이지를 렌더링
    });

    noticeList.addEventListener('click', function (event) {
        if (event.target.classList.contains('edit-button')) {
            const index = event.target.getAttribute('data-index');
            openEditModal(index);
        }

        if (event.target.classList.contains('delete-button')) {
            const index = event.target.getAttribute('data-index');
            $('#deleteConfirmModal').modal('show');
            deleteConfirmButton.setAttribute('data-index', index);
        }
    });

    deleteConfirmButton.addEventListener('click', function () {
        const index = deleteConfirmButton.getAttribute('data-index');
        deleteNotice(index);
    });

    // 초기 렌더링
    renderNotices(notices.sort((a, b) => new Date(b.date) - new Date(a.date)), currentPage);
});
