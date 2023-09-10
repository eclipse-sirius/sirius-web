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
import { gql, useMutation } from '@apollo/client';
import { DRAG_SOURCES_TYPE, SelectionEntry, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  PropertySectionComponentProps,
  PropertySectionLabel,
  useClickHandler,
} from '@eclipse-sirius/sirius-components-forms';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import {
  GQLClearReferenceMutationData,
  GQLClearReferenceMutationVariables,
  GQLClickReferenceValueMutationData,
  GQLClickReferenceValueMutationVariables,
  GQLEditReferenceData,
  GQLEditReferencePayload,
  GQLEditReferenceVariables,
  GQLErrorPayload,
  GQLReferenceValue,
  GQLReferenceWidget,
  GQLRemoveReferenceValueMutationData,
  GQLRemoveReferenceValueMutationVariables,
  GQLSetReferenceValueMutationData,
  GQLSetReferenceValueMutationVariables,
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

export const editReferenceMutation = gql`
  mutation editReference($input: EditReferenceInput!) {
    editReference(input: $input) {
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

export const clickReferenceValueMutation = gql`
  mutation clickReferenceValue($input: ClickReferenceValueInput!) {
    clickReferenceValue(input: $input) {
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

const isErrorPayload = (payload: GQLEditReferencePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditReferencePayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const ReferencePropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
  setSelection,
}: PropertySectionComponentProps<GQLReferenceWidget>) => {
  const classes = useStyles();

  const [editReference, { loading, error, data }] = useMutation<GQLEditReferenceData, GQLEditReferenceVariables>(
    editReferenceMutation
  );

  const [clearReference, { loading: clearLoading, error: clearError, data: clearData }] = useMutation<
    GQLClearReferenceMutationData,
    GQLClearReferenceMutationVariables
  >(clearReferenceMutation);

  const [clickReferenceValue, { loading: clickLoading, error: clickError, data: clickData }] = useMutation<
    GQLClickReferenceValueMutationData,
    GQLClickReferenceValueMutationVariables
  >(clickReferenceValueMutation);

  const [removeReferenceValue, { loading: removeLoading, error: removeError, data: removeData }] = useMutation<
    GQLRemoveReferenceValueMutationData,
    GQLRemoveReferenceValueMutationVariables
  >(removeReferenceValueMutation);

  const [setReferenceValue, { loading: setLoading, error: setError, data: setData }] = useMutation<
    GQLSetReferenceValueMutationData,
    GQLSetReferenceValueMutationVariables
  >(setReferenceValueMutation);

  const onReferenceValueSimpleClick = (item: GQLReferenceValue) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
    if (item.hasClickAction) {
      const variables: GQLClickReferenceValueMutationVariables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          referenceValueId: item.id,
          clickEventKind: 'SINGLE_CLICK',
        },
      };
      clickReferenceValue({ variables });
    }
  };
  const onReferenceValueDoubleClick = (item: GQLReferenceValue) => {
    const { id, label, kind } = item;
    setSelection({ entries: [{ id, label, kind }] });
    if (item.hasClickAction) {
      const variables: GQLClickReferenceValueMutationVariables = {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          referenceValueId: item.id,
          clickEventKind: 'DOUBLE_CLICK',
        },
      };
      clickReferenceValue({ variables });
    }
  };

  const clickHandler = useClickHandler<GQLReferenceValue>(onReferenceValueSimpleClick, onReferenceValueDoubleClick);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editReference: editMultiValuedReference } = data;
        if (isErrorPayload(editMultiValuedReference) || isSuccessPayload(editMultiValuedReference)) {
          addMessages(editMultiValuedReference.messages);
        }
      }
    }
  }, [loading, error, data]);
  useEffect(() => {
    if (!clickLoading) {
      if (clickError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (clickData) {
        const { clickReferenceValue } = clickData;
        if (isErrorPayload(clickReferenceValue) || isSuccessPayload(clickReferenceValue)) {
          addMessages(clickReferenceValue.messages);
        }
      }
    }
  }, [clickLoading, clickError, clickData]);
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

  const callSetReference = (newValueId) => {
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
  };
  const callEditReference = (newValueIds) => {
    const variables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: formId,
        referenceWidgetId: widget.id,
        newValueIds,
      },
    };
    editReference({ variables });
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
          const valueIds = widget.referenceValues.map((referenceValue) => referenceValue.id);

          if (widget.reference.manyValued) {
            callEditReference([...valueIds, ...semanticElementIds]);
          } else {
            // mono-valued reference could not receive multiple elements
            if (semanticElementIds.length > 1) {
              addErrorMessage('Single-valued reference can only accept a single value');
            } else {
              callSetReference(semanticElementIds[0]);
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

  const addSelectedElements = (selectedElementIds: string[]): void => {
    setModalDisplayed(null);
    if (selectedElementIds) {
      callEditReference([...selectedElementIds]);
    }
  };

  const setSelectedElement = (selectedElementId: string) => {
    setModalDisplayed(null);
    if (selectedElementId && selectedElementId.length > 0) {
      callSetReference(selectedElementId);
    }
  };

  const addNewElement = (selectedElementId: string): void => {
    setModalDisplayed(null);
    if (selectedElementId) {
      callEditReference(
        widget.reference.manyValued
          ? [...widget.referenceValues.map((value) => value.id), selectedElementId]
          : [selectedElementId]
      );
    }
  };

  let modal: JSX.Element | null = null;
  if (modalDisplayed === 'browse') {
    modal = widget.reference.manyValued ? (
      <TransferModal editingContextId={editingContextId} onClose={addSelectedElements} widget={widget} />
    ) : (
      <BrowseModal editingContextId={editingContextId} onClose={setSelectedElement} widget={widget} />
    );
  } else if (modalDisplayed === 'create') {
    modal = <CreateModal editingContextId={editingContextId} onClose={addNewElement} widget={widget} />;
  }

  return (
    <>
      <div className={classes.root} data-testid={widget.label}>
        <PropertySectionLabel
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          subscribers={subscribers}
        />
        <ValuedReferenceAutocomplete
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          readOnly={readOnly}
          editReference={editReference}
          onDragEnter={handleDragEnter}
          onDragOver={handleDragOver}
          onDrop={handleDrop}
          onMoreClick={onBrowse}
          onCreateClick={onCreate}
          optionClickHandler={clickHandler}
          clearReference={clearReference}
          removeReferenceValue={removeReferenceValue}
        />
      </div>
      {modal}
    </>
  );
};
