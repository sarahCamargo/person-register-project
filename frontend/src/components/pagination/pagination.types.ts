export interface PaginationProps<T> {
    dados: T[];
    onPageChange: (displayedDados: T[]) => void;
}
