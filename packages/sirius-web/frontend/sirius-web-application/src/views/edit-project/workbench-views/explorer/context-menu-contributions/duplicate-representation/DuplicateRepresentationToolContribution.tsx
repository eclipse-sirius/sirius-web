/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { PaletteToolOverriddenContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import AddToPhotosIcon from '@mui/icons-material/AddToPhotos';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useContext, useEffect } from 'react';
import { useDuplicateRepresentation } from '../useDuplicateRepresentation';

export const DuplicateRepresentationToolContribution = forwardRef(
  ({ onInvoked }: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const { setSelection } = useSelection();
    const { editingContextId, item, readOnly, onClose } = useContext<TreePaletteContextValue>(TreePaletteContext);
    const { duplicateRepresentation, duplicatedRepresentationMetadata } = useDuplicateRepresentation();
    const onDuplicate = () => {
      duplicateRepresentation(editingContextId, item.id);
    };

    useEffect(() => {
      if (duplicatedRepresentationMetadata) {
        setSelection({
          entries: [
            {
              id: duplicatedRepresentationMetadata.id,
            },
          ],
        });
        onClose();
      }
    }, [duplicatedRepresentationMetadata, setSelection]);

    const handleClick = () => {
      onInvoked();
      onDuplicate();
    };

    return (
      <MenuItem
        key="duplicate-representation"
        onClick={handleClick}
        data-testid="duplicate-representation"
        disabled={readOnly}
        ref={ref}
        aria-disabled>
        <ListItemIcon>
          <AddToPhotosIcon fontSize="small" />
        </ListItemIcon>
        <ListItemText primary="Duplicate representation" />
      </MenuItem>
    );
  }
);
