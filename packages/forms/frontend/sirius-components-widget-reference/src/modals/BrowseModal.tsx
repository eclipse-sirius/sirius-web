/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { ModelBrowserTreeView } from '@eclipse-sirius/sirius-components-browser';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { BrowseModalProps } from './BrowseModal.types';

const useBrowserModalStyles = makeStyles()((_) => ({
  content: {
    height: 400,
  },
}));

export const BrowseModal = ({ editingContextId, widget, onClose }: BrowseModalProps) => {
  const { classes: styles } = useBrowserModalStyles();
  const [selectedTreeItemIds, setSelectedTreeItemIds] = useState<string[]>(
    widget.referenceValues.map((value) => value.id)
  );
  const { t } = useTranslation('siriusComponentsWidgetReference', { keyPrefix: 'select' });

  const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem) => {
    if (widget.reference.manyValued) {
      if (event.ctrlKey || event.metaKey) {
        event.stopPropagation();
        if (selectedTreeItemIds.includes(item.id)) {
          setSelectedTreeItemIds((prevState) => prevState.filter((itemId) => itemId !== item.id));
        } else {
          setSelectedTreeItemIds((prevState) => [...prevState, item.id]);
        }
      } else {
        setSelectedTreeItemIds([item.id]);
      }
    } else {
      setSelectedTreeItemIds([item.id]);
    }
  };

  return (
    <Dialog
      open={true}
      onClose={() => onClose(null)}
      aria-labelledby="dialog-title"
      fullWidth
      data-testid="browse-modal">
      <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
      <DialogContent className={styles.content}>
        <ModelBrowserTreeView
          editingContextId={editingContextId}
          referenceKind={widget.reference.referenceKind}
          ownerId={widget.ownerId}
          descriptionId={widget.descriptionId}
          isContainment={widget.reference.containment}
          markedItemIds={[]}
          title={t('choices')}
          leafType={'reference'}
          ownerKind={widget.reference.ownerKind}
          onTreeItemClick={onTreeItemClick}
          selectedTreeItemIds={selectedTreeItemIds}
        />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          color="primary"
          type="button"
          data-testid="select-value"
          onClick={() => {
            const selectedElementId = selectedTreeItemIds?.length > 0 ? selectedTreeItemIds[0] : null;
            if (selectedElementId) {
              onClose(selectedElementId);
            }
          }}>
          {t('submit')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
