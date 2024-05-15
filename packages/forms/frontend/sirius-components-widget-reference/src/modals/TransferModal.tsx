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
import { gql, useQuery } from '@apollo/client';
import { ModelBrowserTreeView } from '@eclipse-sirius/sirius-components-browser';
import { DRAG_SOURCES_TYPE, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { FilterableSortableList } from '../components/FilterableSortableList';
import { FilterableSortableListItem } from '../components/FilterableSortableList.types';
import {
  GQLFormDescription,
  GQLGetReferenceValueOptionsQueryData,
  GQLGetReferenceValueOptionsQueryVariables,
  GQLRepresentationDescription,
} from '../components/ValuedReferenceAutocomplete.types';
import { TransferModalProps, TransferModalState } from './TransferModal.types';

const getReferenceValueOptionsQuery = gql`
  query getReferenceValueOptions($editingContextId: ID!, $representationId: ID!, $referenceWidgetId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on FormDescription {
              referenceValueOptions(referenceWidgetId: $referenceWidgetId) {
                id
                label
                kind
                iconURL
              }
            }
          }
        }
      }
    }
  }
`;

const isFormDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLFormDescription => representationDescription.__typename === 'FormDescription';

export const TransferModal = ({
  editingContextId,
  formId,
  widget,
  onClose,
  addElements,
  removeElement,
  moveElement,
}: TransferModalProps) => {
  const { addErrorMessage } = useMultiToast();
  const [state, setState] = useState<TransferModalState>({
    right: widget.referenceValues,
    rightSelection: [],
    draggingRightItemId: undefined,
    leftSelection: [],
    options: [],
    selectedTreeItemIds: [],
  });
  const { t } = useTranslation('siriusComponentsWidgetReference');

  const {
    loading: childReferenceValueOptionsLoading,
    data: childReferenceValueOptionsData,
    error: childReferenceValueOptionsError,
  } = useQuery<GQLGetReferenceValueOptionsQueryData, GQLGetReferenceValueOptionsQueryVariables>(
    getReferenceValueOptionsQuery,
    {
      variables: {
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
      },
    }
  );

  useEffect(() => {
    if (!childReferenceValueOptionsLoading) {
      if (childReferenceValueOptionsError) {
        addErrorMessage(t('errors.unexpected'));
      }
      if (childReferenceValueOptionsData) {
        const representationDescription: GQLRepresentationDescription =
          childReferenceValueOptionsData.viewer.editingContext.representation.description;
        if (isFormDescription(representationDescription)) {
          setState((prevState) => {
            return {
              ...prevState,
              options: representationDescription.referenceValueOptions,
            };
          });
        }
      }
    }
  }, [childReferenceValueOptionsLoading, childReferenceValueOptionsData, childReferenceValueOptionsError]);

  useEffect(() => {
    setState((prevState) => {
      return {
        ...prevState,
        rightSelection: prevState.rightSelection.filter((sel) =>
          widget.referenceValues.map((ref) => ref.id).includes(sel.id)
        ),
        right: widget.referenceValues,
      };
    });
  }, [widget.referenceValues]);

  const handleDragOverLeft = (event: React.DragEvent) => {
    event.preventDefault();
  };

  const handleDropLeft = (event: React.DragEvent) => {
    event.preventDefault();
    if (state.draggingRightItemId) {
      removeElement(state.draggingRightItemId);
    }
  };

  const handleDragStart = (id: string) => {
    setState((prevState) => {
      return {
        ...prevState,
        draggingRightItemId: id,
      };
    });
  };

  const handleDragEnd = () => {
    setState((prevState) => {
      return {
        ...prevState,
        draggingRightItemId: undefined,
      };
    });
  };

  const handleDropRight = (event: React.DragEvent) => {
    event.preventDefault();
    const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
    if (dragSourcesStringified) {
      const sources = JSON.parse(dragSourcesStringified);
      if (Array.isArray(sources) && sources.length > 0) {
        const newElementIds = sources.filter(
          (newEntryId) => !state.right.some((existingEntry) => existingEntry.id === newEntryId)
        );
        addElements(newElementIds);
      }
    }
  };

  const onClick = (event: React.MouseEvent<Element, MouseEvent>, item: FilterableSortableListItem) => {
    if (event.ctrlKey || event.metaKey) {
      event.stopPropagation();
      const isItemInSelection = state.rightSelection.find((entry) => entry.id === item.id);
      const newSelection: FilterableSortableListItem[] = isItemInSelection
        ? state.rightSelection.filter((entry) => entry.id !== item.id)
        : [...state.rightSelection, item];
      setState((prevState) => {
        return {
          ...prevState,
          rightSelection: newSelection,
          selectedTreeItemIds: newSelection.map((selection) => selection.id),
        };
      });
    } else {
      setState((prevState) => {
        return {
          ...prevState,
          rightSelection: [item],
          selectedTreeItemIds: [item.id],
        };
      });
    }
  };

  const handleDispatchRight = () => {
    addElements(state.selectedTreeItemIds);
  };

  const handleDispatchLeft = () => {
    state.rightSelection.forEach((element) => removeElement(element.id));
  };

  const onTreeItemClick = (event, item: GQLTreeItem) => {
    if (widget.reference.manyValued) {
      if (event.ctrlKey || event.metaKey) {
        event.stopPropagation();
        if (state.selectedTreeItemIds.includes(item.id)) {
          setState((prevState) => ({
            ...prevState,
            selectedTreeItemIds: prevState.selectedTreeItemIds.filter((itemId) => itemId !== item.id),
          }));
        } else {
          setState((prevState) => ({
            ...prevState,
            selectedTreeItemIds: [...prevState.selectedTreeItemIds, item.id],
          }));
        }
      } else {
        setState((prevState) => ({ ...prevState, selectedTreeItemIds: [item.id] }));
      }
    } else {
      setState((prevState) => ({ ...prevState, selectedTreeItemIds: [item.id] }));
    }
  };

  return (
    <Dialog
      open={true}
      onClose={() => onClose()}
      aria-labelledby="dialog-title"
      maxWidth="lg"
      fullWidth
      data-testid="transfer-modal">
      <DialogTitle id="dialog-title">{t('edit.title')}</DialogTitle>
      <DialogContent>
        <Box
          sx={(theme) => ({
            display: 'grid',
            gridTemplateRows: '1fr',
            gridTemplateColumns: '1fr min-content 1fr',
            gap: theme.spacing(1),
          })}>
          <div onDragOver={handleDragOverLeft} onDrop={handleDropLeft}>
            <ModelBrowserTreeView
              editingContextId={editingContextId}
              referenceKind={widget.reference.referenceKind}
              ownerId={widget.ownerId}
              descriptionId={widget.descriptionId}
              isContainment={widget.reference.containment}
              markedItemIds={state.right.map((entry) => entry.id)}
              title={t('edit.choices')}
              leafType={'reference'}
              ownerKind={widget.reference.ownerKind}
              onTreeItemClick={onTreeItemClick}
              selectedTreeItemIds={state.selectedTreeItemIds}
            />
          </div>

          <Box
            sx={(theme) => ({
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'center',
              gap: theme.spacing(1),
            })}>
            <IconButton
              onClick={handleDispatchRight}
              disabled={
                !state.selectedTreeItemIds.some(
                  (selectedId) => !state.right.some((rightEntry) => rightEntry.id === selectedId)
                )
              }
              aria-label="move selected right"
              data-testid="move-right">
              <ChevronRightIcon />
            </IconButton>
            <IconButton
              onClick={handleDispatchLeft}
              disabled={state.rightSelection.length === 0}
              aria-label="move selected left"
              data-testid="move-left">
              <ChevronLeftIcon />
            </IconButton>
          </Box>

          <FilterableSortableList
            items={state.right}
            options={[...state.options, ...widget.referenceValues]}
            setItems={(items: FilterableSortableListItem[]) =>
              setState((prevState) => {
                return {
                  ...prevState,
                  right: items,
                };
              })
            }
            handleDragItemStart={handleDragStart}
            handleDragItemEnd={handleDragEnd}
            handleDropNewItem={handleDropRight}
            onClick={onClick}
            selectedItems={state.rightSelection}
            moveElement={moveElement}
          />
        </Box>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          color="primary"
          type="button"
          data-testid="close-transfer-modal"
          onClick={() => onClose()}>
          {t('edit.close')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
