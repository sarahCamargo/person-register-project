import React, { useEffect, useState } from 'react';
import { PaginationProps } from './pagination.types';
import { StyledPagination } from './pagination.styles';

const Pagination = <T,>({ dados, onPageChange }: PaginationProps<T>) => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);

    const handleChangePage = (_event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    useEffect(() => {
        const displayedDados = dados?.slice(
            page * rowsPerPage,
            page * rowsPerPage + rowsPerPage
        );
        onPageChange(displayedDados)
    }, [page, rowsPerPage, dados, onPageChange]);

    return (
        <StyledPagination
            rowsPerPageOptions={[5, 10, 15]}
            count={dados ? dados.length : 0}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
            labelRowsPerPage=""
        />
    )
}

export default Pagination;