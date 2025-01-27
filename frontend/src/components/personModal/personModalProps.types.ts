import { Pessoa } from "../../types/pessoa";

export interface PersonModalProps {
  refreshList: () => void;
  open: boolean;
  onClose: () => void;
  onAdd: (person: Pessoa) => Promise<void>;
  onEdit: (person: Pessoa) => Promise<void>;
  personToEdit?: Pessoa;
}