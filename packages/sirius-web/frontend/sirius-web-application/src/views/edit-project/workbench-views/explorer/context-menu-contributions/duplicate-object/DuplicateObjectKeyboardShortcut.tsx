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
  selectedTreeItem,
  selectTreeItems,
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
    if ((e.ctrlKey || e.metaKey) && e.code === 'KeyD') {
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
    selectTreeItems(selection.entries.map((entry) => entry.id));
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
