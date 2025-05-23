export default class Board {
  constructor({ url, $tbody, $pagination, renderRow, $pageSizeSelect, $sortSelect }) {
    this.url = url;
    this.$tbody = $tbody;
    this.$pagination = $pagination;
    this.renderRow = renderRow;

    this.currentPage = 0;
    this.pageSize = 10;
    this.sort = ['id', 'desc'];

    this.dataList = [];
    this.pagination = {};

    if ($pageSizeSelect) {
      this.$pageSizeSelect = $pageSizeSelect;
      $pageSizeSelect.on('change', () => {
        this.pageSize = parseInt($pageSizeSelect.val(), 10);
        this.loadPage(0);
      });
    }

    if ($sortSelect) {
      this.$sortSelect = $sortSelect;
      $sortSelect.on('change', () => {
        this.sort = $sortSelect.val().split(',');
        this.loadPage(0);
      });
    }

    this.loadPage(0);
  }

  loadPage(pageNumber) {
    this.currentPage = pageNumber;

    $.ajax({
      type: 'GET',
      url: this.url,
      data: {
        page: this.currentPage,
        size: this.pageSize,
        sort: this.sort,
      },
      success: (response) => {
        this.dataList = response.data;
        this.pagination = response.pagination;
        this.reset();
        this.setBoard();
        this.setPagination();
      },
      error: (jqXHR) => {
        alert("데이터 로딩 실패");
        console.error(jqXHR);
      }
    });
  }

  reset() {
    this.$tbody.empty();
    this.$pagination.empty();
  }

  setBoard() {
    this.dataList.forEach(item => {
      const $row = this.renderRow(item);
      this.$tbody.append($row);
    });
  }

  setPagination() {
    const currentSet = Math.floor(this.currentPage / 10);
    const startPage = currentSet * 10;
    const endPage = Math.min(this.pagination.totalPages, startPage + 10);

    const $prev = $('<li><a href="#">&laquo;</a></li>').on('click', () => {
      if (startPage > 0) this.loadPage(startPage - 1);
      else alert("이전 페이지가 없습니다.")
    });

    const $next = $('<li><a href="#">&raquo;</a></li>').on('click', () => {
      if (endPage < this.pagination.totalPages) this.loadPage(endPage);
      else alert("다음 페이지가 없습니다.")
    });

    this.$pagination.append($prev);

    for (let i = startPage; i < endPage; i++) {
      const $page = $(`<li><a href="#">${i + 1}</a></li>`);
      if (i === this.currentPage) $page.addClass('active');
      $page.on('click', () => this.loadPage(i));
      this.$pagination.append($page);
    }

    this.$pagination.append($next);
  }

  deleteRow(id) {
    if (confirm('정말 삭제하시겠습니까?')) {
      $.ajax({
        type: 'DELETE',
        url: `${this.url}/${id}`,
        success: () => {
          alert('삭제 성공');
          this.loadPage(this.currentPage);
        },
        error: () => {
          alert('삭제 실패');
        }
      });
    }
  }
}
