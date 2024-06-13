/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import {
  DRAG_SOURCES_TYPE,
  SelectionEntry,
  useDeletionConfirmationDialog,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import {
  GQLWidget,
  PropertySectionComponent,
  PropertySectionComponentProps,
  PropertySectionLabel,
  useClickHandler,
} from '@eclipse-sirius/sirius-components-forms';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import {
  GQLAddReferenceValuesMutationData,
  GQLAddReferenceValuesMutationVariables,
  GQLAddReferenceValuesPayload,
  GQLClearReferenceMutationData,
  GQLClearReferenceMutationVariables,
  GQLClearReferencePayload,
  GQLErrorPayload,
  GQLMoveReferenceValueMutationData,
  GQLMoveReferenceValueMutationVariables,
  GQLMoveReferenceValuePayload,
  GQLReferenceValue,
  GQLReferenceWidget,
  GQLRemoveReferenceValueMutationData,
  GQLRemoveReferenceValueMutationVariables,
  GQLRemoveReferenceValuePayload,
  GQLSetReferenceValueMutationData,
  GQLSetReferenceValueMutationVariables,
  GQLSetReferenceValuePayload,
  GQLSuccessPayload,
} from './ReferenceWidgetFragment.types';
import { ValuedReferenceAutocomplete } from './components/ValuedReferenceAutocomplete';
import { BrowseModal } from './modals/BrowseModal';
import { CreateModal } from './modals/CreateModal';
import { TransferModal } from './modals/TransferModal';

const useStyles = makeStyles<Theme>(() => ({
  root: {
    overflow: 'hidden',
  },
}));

export const clearReferenceMutation = gql`
  mutation clearReference($input: ClearReferenceInput!) {
    clearReference(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const removeReferenceValueMutation = gql`
  mutation removeReferenceValue($input: RemoveReferenceValueInput!) {
    removeReferenceValue(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const setReferenceValueMutation = gql`
  mutation setReferenceValue($input: SetReferenceValueInput!) {
    setReferenceValue(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const addReferenceValuesMutation = gql`
  mutation addReferenceValues($input: AddReferenceValuesInput!) {
    addReferenceValues(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const moveReferenceValueMutation = gql`
  mutation moveReferenceValue($input: MoveReferenceValueInput!) {
    moveReferenceValue(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (
  payload:
    | GQLClearReferencePayload
    | GQLRemoveReferenceValuePayload
    | GQLSetReferenceValuePayload
    | GQLAddReferenceValuesPayload
    | GQLMoveReferenceValuePayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload:
    | GQLClearReferencePayload
    | GQLRemoveReferenceValuePayload
    | GQLSetReferenceValuePayload
    | GQLAddReferenceValuesPayload
    | GQLMoveReferenceValuePayload
): payload is GQLSuccessPayload => payload.__typename === 'SuccessPayload';

const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

export const ReferencePropertySection: PropertySectionComponent<GQLWidget> = ({
  widget,
  ...props
}: PropertySectionComponentProps<GQLWidget>) => {
  if (isReferenceWidget(widget)) {
    return <RawReferencePropertySection widget={widget} {...props} />;
  }
  return null;
};

const RawReferencePropertySection: PropertySectionComponent<GQLReferenceWidget> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLReferenceWidget>) => {
  const classes = useStyles();
  const { setSelection } = useSelection();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const [clearReference, { loading: clearLoading, error: clearError, data: clearData }] = useMutation<
    GQLClearReferenceMutationData,
    GQLClearReferenceMutationVariables
  >(clearReferenceMutation);

  const [removeReferenceValue, { loading: removeLoading, error: removeError, data: removeData }] = useMutation<
    GQLRemoveReferenceValueMutationData,
    GQLRemoveReferenceValueMutationVariables
  >(removeReferenceValueMutation);

  const [setReferenceValue, { loading: setLoading, error: setError, data: setData }] = useMutation<
    GQLSetReferenceValueMutationData,
    GQLSetReferenceValueMutationVariables
  >(setReferenceValueMutation);

  const [addReferenceValues, { loading: addLoading, error: addError, data: addData }] = useMutation<
    GQLAddReferenceValuesMutationData,
    GQLAddReferenceValuesMutationVariables
  >(addReferenceValuesMutation);

  const [moveReferenceValue, { loading: moveLoading, error: moveError, data: moveData }] = useMutation<
    GQLMoveReferenceValueMutationData,
    GQLMoveReferenceValueMutationVariables
  >(moveReferenceValueMutation);

  const onReferenceValueSimpleClick = (item: GQLReferenceValue) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
  };
  const onReferenceValueDoubleClick = (item: GQLReferenceValue) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
  };

  const clickHandler = useClickHandler<GQLReferenceValue>(onReferenceValueSimpleClick, onReferenceValueDoubleClick);

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!clearLoading) {
      if (clearError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (clearData) {
        const { clearReference } = clearData;
        if (isErrorPayload(clearReference) || isSuccessPayload(clearReference)) {
          addMessages(clearReference.messages);
        }
      }
    }
  }, [clearLoading, clearError, clearData]);
  useEffect(() => {
    if (!removeLoading) {
      if (removeError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (removeData) {
        const { removeReferenceValue } = removeData;
        if (isErrorPayload(removeReferenceValue) || isSuccessPayload(removeReferenceValue)) {
          addMessages(removeReferenceValue.messages);
        }
      }
    }
  }, [removeLoading, removeError, removeData]);
  useEffect(() => {
    if (!setLoading) {
      if (setError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (setData) {
        const { setReferenceValue } = setData;
        if (isErrorPayload(setReferenceValue) || isSuccessPayload(setReferenceValue)) {
          addMessages(setReferenceValue.messages);
        }
      }
    }
  }, [setLoading, setError, setData]);
  useEffect(() => {
    if (!addLoading) {
      if (addError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (addData) {
        const { addReferenceValues } = addData;
        if (isErrorPayload(addReferenceValues) || isSuccessPayload(addReferenceValues)) {
          addMessages(addReferenceValues.messages);
        }
      }
    }
  }, [addLoading, addError, addData]);
  useEffect(() => {
    if (!moveLoading) {
      if (moveError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (moveData) {
        const { moveReferenceValue } = moveData;
        if (isErrorPayload(moveReferenceValue) || isSuccessPayload(moveReferenceValue)) {
          addMessages(moveReferenceValue.messages);
        }
      }
    }
  }, [moveLoading, moveError, moveData]);

  const callClearReference = () => {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
      },
    };
    if (widget.reference.containment) {
      showDeletionConfirmation(() => {
        clearReference({ variables });
      });
    } else {
      clearReference({ variables });
    }
  };

  const callSetReferenceValue = (newValueId: string | null) => {
    if (newValueId) {
      const variables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          newValueId: newValueId,
        },
      };
      setReferenceValue({ variables });
    }
  };

  const callAddReferenceValues = (newValueIds: string[]) => {
    if (newValueIds) {
      const variables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          newValueIds,
        },
      };
      addReferenceValues({ variables });
    }
  };

  const callRemoveReferenceValue = (valueId) => {
    if (valueId) {
      const variables: GQLRemoveReferenceValueMutationVariables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          referenceValueId: valueId,
        },
      };
      if (widget.reference.containment) {
        showDeletionConfirmation(() => {
          removeReferenceValue({ variables });
        });
      } else {
        removeReferenceValue({ variables });
      }
    }
  };

  const callMoveReferenceValue = (valueId: string, fromIndex: number, toIndex: number) => {
    if (valueId && fromIndex !== -1 && toIndex !== -1) {
      if (valueId) {
        const variables: GQLMoveReferenceValueMutationVariables = {
          input: {
            id: crypto.randomUUID(),
            editingContextId,
            representationId: formId,
            referenceWidgetId: widget.id,
            referenceValueId: valueId,
            fromIndex,
            toIndex,
          },
        };
        moveReferenceValue({ variables });
      }
    }
  };

  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
  };

  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
  };

  const handleDrop = (event: React.DragEvent) => {
    event.preventDefault();
    if (readOnly || widget.readOnly) {
      addErrorMessage('This widget is currently read-only');
    } else {
      const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
      if (dragSourcesStringified) {
        const sources = JSON.parse(dragSourcesStringified);
        if (Array.isArray(sources) && sources.length > 0) {
          const entries = sources as SelectionEntry[];
          const semanticElementIds = entries
            .filter((entry) => entry.kind.startsWith('siriusComponents://semantic?'))
            .map((entry) => entry.id);
          if (widget.reference.manyValued) {
            callAddReferenceValues([...semanticElementIds]);
          } else {
            // mono-valued reference could not receive multiple elements
            if (semanticElementIds.length > 1) {
              addErrorMessage('Single-valued reference can only accept a single value');
            } else {
              callSetReferenceValue(semanticElementIds[0] ?? null);
            }
          }
        }
      }
    }
  };

  const [modalDisplayed, setModalDisplayed] = useState<string | null>(null);

  const onBrowse = () => {
    setModalDisplayed('browse');
  };

  const onCreate = () => {
    setModalDisplayed('create');
  };

  const setSelectedElement = (selectedElementId: string | null) => {
    setModalDisplayed(null);
    if (selectedElementId && selectedElementId.length > 0) {
      callSetReferenceValue(selectedElementId);
    }
  };

  const addNewElement = (selectedElementId: string | null): void => {
    setModalDisplayed(null);
    if (selectedElementId) {
      if (widget.reference.manyValued) {
        callAddReferenceValues([selectedElementId]);
      } else {
        callSetReferenceValue(selectedElementId);
      }
    }
  };

  let modal: JSX.Element | null = null;
  if (modalDisplayed === 'browse') {
    modal = widget.reference.manyValued ? (
      <TransferModal
        editingContextId={editingContextId}
        formId={formId}
        onClose={() => setModalDisplayed(null)}
        addElements={callAddReferenceValues}
        removeElement={callRemoveReferenceValue}
        moveElement={callMoveReferenceValue}
        widget={widget}
      />
    ) : (
      <BrowseModal editingContextId={editingContextId} onClose={setSelectedElement} widget={widget} />
    );
  } else if (modalDisplayed === 'create') {
    modal = <CreateModal editingContextId={editingContextId} onClose={addNewElement} widget={widget} formId={formId} />;
  }

  return (
    <>
      <div className={classes.root}>
        <PropertySectionLabel
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          data-testid={widget.label + '_' + widget.reference.referenceKind}
        />
        <ValuedReferenceAutocomplete
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          readOnly={readOnly}
          onDragEnter={handleDragEnter}
          onDragOver={handleDragOver}
          onDrop={handleDrop}
          onMoreClick={onBrowse}
          onCreateClick={onCreate}
          optionClickHandler={clickHandler}
          clearReference={callClearReference}
          removeReferenceValue={callRemoveReferenceValue}
          addReferenceValues={callAddReferenceValues}
          setReferenceValue={callSetReferenceValue}
        />
      </div>
      {modal}
    </>
  );
};
