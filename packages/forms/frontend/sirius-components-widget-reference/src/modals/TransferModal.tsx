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
import { gql, useQuery } from '@apollo/client';
import { DRAG_SOURCES_TYPE, SelectionEntry, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import React, { useEffect, useState } from 'react';
import { FilterableSortableList } from '../components/FilterableSortableList';
import { ModelBrowserTreeView } from '../components/ModelBrowserTreeView';
import {
  GQLFormDescription,
  GQLGetReferenceValueOptionsQueryData,
  GQLGetReferenceValueOptionsQueryVariables,
  GQLRepresentationDescription,
} from '../components/ValuedReferenceAutocomplete.types';
import { TransferModalProps, TransferModalState } from './TransferModal.types';

const useStyles = makeStyles((theme) => ({
  dialogContent: {
    overflowX: 'hidden',
  },
  root: {
    margin: 'auto',
  },
  paper: {
    width: 400,
    height: 370,
    overflow: 'auto',
  },
  button: {
    margin: theme.spacing(0.5, 0),
  },
}));

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
  const classes = useStyles();
  const { addErrorMessage } = useMultiToast();
  const [state, setState] = useState<TransferModalState>({
    right: widget.referenceValues,
    rightSelection: [],
    draggingRightItemId: undefined,
    leftSelection: [],
    options: [],
  });

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
        addErrorMessage('An unexpected error has occurred, please refresh the page');
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

  const handleLeftSelection = (selection) => {
    setState((prevState) => {
      return {
        ...prevState,
        rightSelection: [],
        leftSelection: selection.entries,
      };
    });
  };

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
        const entriesDragged = sources as SelectionEntry[];
        const newElementIds = entriesDragged
          .filter((newEntry) => !state.right.some((existingEntry) => existingEntry.id === newEntry.id))
          .map((element) => element.id);
        addElements(newElementIds);
      }
    }
  };

  const onClick = (event: React.MouseEvent<Element, MouseEvent>, item: SelectionEntry) => {
    if (event.ctrlKey || event.metaKey) {
      event.stopPropagation();
      const isItemInSelection = state.rightSelection.find((entry) => entry.id === item.id);
      const newSelection: SelectionEntry[] = isItemInSelection
        ? state.rightSelection.filter((entry) => entry.id !== item.id)
        : [...state.rightSelection, item];
      setState((prevState) => {
        return {
          ...prevState,
          rightSelection: newSelection,
          leftSelection: newSelection,
        };
      });
    } else {
      setState((prevState) => {
        return {
          ...prevState,
          rightSelection: [item],
          leftSelection: [item],
        };
      });
    }
  };

  const handleDispatchRight = () => {
    addElements(state.leftSelection.map((element) => element.id));
  };

  const handleDispatchLeft = () => {
    state.rightSelection.forEach((element) => removeElement(element.id));
  };

  return (
    <Dialog
      open={true}
      onClose={() => onClose()}
      aria-labelledby="dialog-title"
      maxWidth={false}
      data-testid="transfer-modal">
      <DialogTitle id="dialog-title">Edit reference</DialogTitle>
      <DialogContent className={classes.dialogContent}>
        <Grid container spacing={2} justifyContent="center" alignItems="center" className={classes.root}>
          <Grid item>
            <div className={classes.paper} onDragOver={handleDragOverLeft} onDrop={handleDropLeft}>
              <ModelBrowserTreeView
                editingContextId={editingContextId}
                selection={{ entries: state.leftSelection }}
                setSelection={handleLeftSelection}
                widget={widget}
                markedItemIds={state.right.map((entry) => entry.id)}
                enableMultiSelection={widget.reference.manyValued}
                title={'Choices'}
                leafType={'reference'}
                ownerKind={widget.reference.ownerKind}
              />
            </div>
          </Grid>
          <Grid item>
            <Grid container direction="column" alignItems="center">
              <IconButton
                className={classes.button}
                onClick={handleDispatchRight}
                disabled={
                  !state.leftSelection.some(
                    (leftEntry) => !state.right.some((rightEntry) => rightEntry.id === leftEntry.id)
                  )
                }
                aria-label="move selected right"
                data-testid="move-right">
                <ChevronRightIcon />
              </IconButton>
              <IconButton
                className={classes.button}
                onClick={handleDispatchLeft}
                disabled={state.rightSelection.length === 0}
                aria-label="move selected left"
                data-testid="move-left">
                <ChevronLeftIcon />
              </IconButton>
            </Grid>
          </Grid>
          <Grid item>
            <div className={classes.paper}>
              <FilterableSortableList
                items={state.right}
                options={[...state.options, ...widget.referenceValues]}
                setItems={(items: SelectionEntry[]) =>
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
            </div>
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          color="primary"
          type="button"
          data-testid="close-transfer-modal"
          onClick={() => onClose()}>
          close
        </Button>
      </DialogActions>
    </Dialog>
  );
};
