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
import { useMutation } from '@apollo/client';
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { PropertySectionComponentProps, PropertySectionLabel } from '@eclipse-sirius/sirius-components-forms';
import IconButton from '@material-ui/core/IconButton';
import Slider from '@material-ui/core/Slider';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import gql from 'graphql-tag';
import { useContext, useEffect, useState } from 'react';
import { GQLSlider } from './SliderFragment.types';
import {
  GQLEditSliderInput,
  GQLEditSliderMutationData,
  GQLEditSliderMutationVariables,
  GQLEditSliderPayload,
  GQLErrorPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
} from './SliderPropertySection.types';

export const editSliderMutation = gql`
  mutation editSlider($input: EditSliderInput!) {
    editSlider(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLEditSliderPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const SliderPropertySection = ({
  editingContextId,
  formId,
  widget,
  subscribers,
  readOnly,
}: PropertySectionComponentProps<GQLSlider>) => {
  const { httpOrigin }: ServerContextValue = useContext(ServerContext);

  const [message, setMessage] = useState<string | null>(null);

  const [editSlider, { loading, data, error }] = useMutation<GQLEditSliderMutationData, GQLEditSliderMutationVariables>(
    editSliderMutation
  );

  useEffect(() => {
    if (!loading) {
      if (error) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editSlider } = data;
        if (isErrorPayload(editSlider)) {
          setMessage(editSlider.message);
        }
      }
    }
  }, [loading, error, data]);
  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData, GQLUpdateWidgetFocusMutationVariables>(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          const { message } = updateWidgetFocus;
          setMessage(message);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => {
    sendUpdateWidgetFocus(false);
  };

  const onValueChanged = (_, value) => {
    const input: GQLEditSliderInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      sliderId: widget.id,
      newValue: value,
    };
    const variables: GQLEditSliderMutationVariables = { input };
    editSlider({ variables });
  };

  const [displayedValue, setDisplayedValue] = useState<number>(widget.currentValue);
  useEffect(() => {
    setDisplayedValue(widget.currentValue);
  }, [widget.currentValue]);

  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} data-testid={widget.label} />
      <Slider
        data-testid={widget.label}
        disabled={readOnly}
        onFocus={onFocus}
        onBlur={onBlur}
        min={widget.minValue}
        max={widget.maxValue}
        value={displayedValue}
        step={1}
        valueLabelDisplay="on"
        onChange={(_, value) => setDisplayedValue(value as number)}
        onChangeCommitted={onValueChanged}></Slider>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
