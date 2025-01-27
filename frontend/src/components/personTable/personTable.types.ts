import { Pessoa } from "../../types/pessoa";

export interface PersonTableProps {
  pessoas: Pessoa[];
  onEdit: (pessoa: Pessoa) => void;
  onDelete: (id: string) => void;
}
