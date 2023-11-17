/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { memo } from 'react';
import { DiagramDirectEditInput } from '../direct-edit/DiagramDirectEditInput';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { LabelContext } from './LabelContext';
import { LabelProps } from './Label.types';

export const Label = memo(({ diagramElementId, label, children }: LabelProps) => {
  const { currentlyEditedLabelId, editingKey, resetDirectEdit } = useDiagramDirectEdit();

  const handleClose = () => {
    resetDirectEdit();
    const diagramElement = document.querySelector(`[data-id="${diagramElementId}"]`);
    if (diagramElement instanceof HTMLElement) {
      diagramElement.focus();
    }
  };

  if (label.id === currentlyEditedLabelId) {
    return <DiagramDirectEditInput editingKey={editingKey} onClose={handleClose} labelId={label.id} transform={''} />;
  }

  return (
    <LabelContext.Provider value={label}>
      <div data-id={label.id} data-testid={`Label - ${label.text}`} className="nopan">
        {children}
      </div>
    </LabelContext.Provider>
  );
});
