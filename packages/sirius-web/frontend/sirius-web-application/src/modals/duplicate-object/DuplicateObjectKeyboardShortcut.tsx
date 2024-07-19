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
import { useEffect, useState } from 'react';
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { DuplicateObjectModal } from './DuplicateObjectModal';
import { KeyboardShortcutProps } from './DuplicateObjectKeyboardShortcut.types';

export const DuplicateObjectKeyboardShortcut = ({
  editingContextId,
  readOnly,
  selectedTreeItem,
  children,
}: KeyboardShortcutProps) => {
  const [open, setOpen] = useState<boolean>(false);
  const { setSelection } = useSelection();

  const duplicateObjectModalOpen = () => {
    if (selectedTreeItem && selectedTreeItem.kind.startsWith('siriusComponents://semantic')) {
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
  }, [selectedTreeItem, readOnly]);

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
      {open && selectedTreeItem ? (
        <DuplicateObjectModal
          editingContextId={editingContextId}
          objectToDuplicateId={selectedTreeItem.id}
          objectToDuplicateKind={selectedTreeItem.kind}
          onObjectDuplicated={onObjectCreated}
          onClose={onClose}
        />
      ) : null}
    </>
  );
};
