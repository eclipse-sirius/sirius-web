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
import { makeStyles, Theme } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { ValuedReferenceAutocomplete } from './components/ValuedReferenceAutocomplete';
import { BrowseModal } from './modals/BrowseModal';
import { CreateModal } from './modals/CreateModal';
import { TransferModal } from './modals/TransferModal';
import {
  GQLClickReferenceValueMutationData,
  GQLClickReferenceValueMutationVariables,
  GQLEditReferenceData,
  GQLEditReferencePayload,
  GQLEditReferenceVariables,
  GQLErrorPayload,
  GQLReferenceValue,
  GQLReferenceWidget,
  GQLSuccessPayload,
} from './ReferenceWidgetFragment.types';

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

  const [clickReferenceValue, { loading: clickLoading, error: clickError, data: clickData }] = useMutation<
    GQLClickReferenceValueMutationData,
    GQLClickReferenceValueMutationVariables
  >(clickReferenceValueMutation);

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

          let newValueIds;
          if (widget.reference.manyValued) {
            newValueIds = [...valueIds, ...semanticElementIds];
          } else {
            // We let the backend decide how to handle it if there are multiple values.
            newValueIds = [...semanticElementIds];
          }

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
      let newValueIds = [...selectedElementIds];

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
    }
  };

  const addNewElement = (selectedElementId: string): void => {
    setModalDisplayed(null);
    if (selectedElementId) {
      let newValueIds = widget.reference.manyValued
        ? [...widget.referenceValues.map((value) => value.id), selectedElementId]
        : [selectedElementId];

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
    }
  };

  let modal: JSX.Element | null = null;
  if (modalDisplayed === 'browse') {
    modal = widget.reference.manyValued ? (
      <TransferModal editingContextId={editingContextId} onClose={addSelectedElements} widget={widget} />
    ) : (
      <BrowseModal editingContextId={editingContextId} onClose={addSelectedElements} widget={widget} />
    );
  } else if (modalDisplayed === 'create') {
    modal = <CreateModal editingContextId={editingContextId} onClose={addNewElement} widget={widget} />;
  }

  return (
    <>
      <div className={classes.root}>
        <PropertySectionLabel
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          subscribers={subscribers}
          data-testid={widget.label + '_' + widget.reference.typeName + '.' + widget.reference.referenceName}
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
        />
      </div>
      {modal}
    </>
  );
};
