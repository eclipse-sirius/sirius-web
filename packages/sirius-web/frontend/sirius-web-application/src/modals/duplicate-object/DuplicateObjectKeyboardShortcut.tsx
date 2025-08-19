/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { KeyboardShortcutProps } from './DuplicateObjectKeyboardShortcut.types';
import { DuplicateObjectModal } from './DuplicateObjectModal';

export const DuplicateObjectKeyboardShortcut = ({
  editingContextId,
  readOnly,
  selectedTreeItemId,
  selectedTreeItemKind,
  children,
}: KeyboardShortcutProps) => {
  const [open, setOpen] = useState<boolean>(false);
  const { setSelection } = useSelection();

  const duplicateObjectModalOpen = () => {
    if (selectedTreeItemKind?.startsWith('siriusComponents://semantic')) {
      setOpen(true);
    }
  };

  const duplicateObjectKeyPressHandler = (e) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'd') {
      e.preventDefault();
      duplicateObjectModalOpen();
    }
  };

  useEffect(() => {
    if (!readOnly) {
      window.addEventListener('keydown', duplicateObjectKeyPressHandler);
    }
    return () => window.removeEventListener('keydown', duplicateObjectKeyPressHandler);
  }, [selectedTreeItemId, selectedTreeItemKind, readOnly]);

  const onObjectCreated = (selection: Selection) => {
    setSelection(selection);
    setOpen(false);
  };

  const onClose = () => {
    setOpen(false);
  };

  return (
    <>
      {children}
      {open && selectedTreeItemId && selectedTreeItemKind ? (
        <DuplicateObjectModal
          editingContextId={editingContextId}
          objectToDuplicateId={selectedTreeItemId}
          objectToDuplicateKind={selectedTreeItemKind}
          onObjectDuplicated={onObjectCreated}
          onClose={onClose}
        />
      ) : null}
    </>
  );
};
